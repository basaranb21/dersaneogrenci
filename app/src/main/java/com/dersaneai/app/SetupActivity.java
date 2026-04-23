package com.dersaneai.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * İlk açılışta dershanenin sunucu URL'ini ister ve kaydeder.
 * Kaydedilmiş URL varsa doğrudan MainActivity'e yönlendirir.
 */
public class SetupActivity extends AppCompatActivity {

    public static final String PREFS = "dersaneai_prefs";
    public static final String KEY_SERVER_URL = "server_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Zaten URL kaydedildiyse doğrudan ana aktiviteye git
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String kayitliUrl = prefs.getString(KEY_SERVER_URL, "");
        if (!kayitliUrl.isEmpty() && getIntent().getBooleanExtra("yeniden", false) == false) {
            baslat(kayitliUrl);
            return;
        }

        setContentView(R.layout.activity_setup);

        EditText urlInput = findViewById(R.id.urlInput);
        Button baslatBtn = findViewById(R.id.baslatBtn);
        Button testBtn = findViewById(R.id.testBtn);
        TextView durum = findViewById(R.id.durum);

        if (!kayitliUrl.isEmpty()) {
            urlInput.setText(kayitliUrl);
        } else {
            urlInput.setText("http://192.168.1.1:8765/");
        }

        testBtn.setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            if (url.isEmpty()) { durum.setText("Lütfen URL gir"); return; }
            durum.setText("⏳ Test ediliyor...");
            new Thread(() -> {
                boolean ok = urlTest(url);
                runOnUiThread(() -> {
                    if (ok) {
                        durum.setText("✅ Bağlantı başarılı!");
                        durum.setTextColor(0xFF10B981);
                    } else {
                        durum.setText("❌ Bağlanılamadı — IP/port doğru mu? Aynı Wi-Fi'de misin?");
                        durum.setTextColor(0xFFEF4444);
                    }
                });
            }).start();
        });

        baslatBtn.setOnClickListener(v -> {
            String url = urlInput.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(this, "URL boş olamaz", Toast.LENGTH_SHORT).show();
                return;
            }
            // http:// yoksa ekle
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            // Sonunda / ekle
            if (!url.endsWith("/")) url = url + "/";

            prefs.edit().putString(KEY_SERVER_URL, url).apply();
            baslat(url);
        });
    }

    private void baslat(String url) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("url", url);
        startActivity(i);
        finish();
    }

    private boolean urlTest(String url) {
        try {
            if (!url.startsWith("http")) url = "http://" + url;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            int kod = conn.getResponseCode();
            return kod < 500;
        } catch (Exception e) {
            return false;
        }
    }
}
