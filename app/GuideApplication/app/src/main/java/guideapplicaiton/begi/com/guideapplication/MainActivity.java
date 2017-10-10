package guideapplicaiton.begi.com.guideapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;

public class MainActivity extends Activity implements View.OnClickListener {

    EditText eIsim, eTelefon, eGetir;
    Button bKisiGetir, bEkle, bSil, bGetir, bGuncelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eIsim = (EditText) findViewById(R.id.eIsim);
        eTelefon = (EditText) findViewById(R.id.eTelefon);
        eGetir = (EditText) findViewById(R.id.eGetir);
        //Button Kısmı
        bEkle = (Button) findViewById(R.id.bEkle);
        bGuncelle = (Button) findViewById(R.id.bGuncelle);
        bSil = (Button) findViewById(R.id.bSil);
        bGetir = (Button) findViewById(R.id.bGetir);
        bKisiGetir = (Button) findViewById(R.id.bKisiGetir);
        bEkle.setOnClickListener(this);
        bSil.setOnClickListener(this);
        bGuncelle.setOnClickListener(this);
        bGetir.setOnClickListener(this);
        bKisiGetir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bEkle:
                String ad = eIsim.getText().toString();
                String telefon = eTelefon.getText().toString();

                try {
                    VeriTabani db = new VeriTabani(MainActivity.this);
                    db.baglantiyiAc();
                    db.isimTelBilgisiniKaydet(ad, telefon);
                    db.baglantiyiKapat();
                } catch (Exception e) {
                    // TODO: handle exception
                    Dialog hata = new Dialog(this);
                    hata.setTitle("Ekleme İşlemi");
                    TextView tvHata = new TextView(this);
                    tvHata.setText(e.toString());
                    hata.setContentView(tvHata);
                    hata.show();

                } finally {
                    Dialog dialog = new Dialog(this);
                    dialog.setTitle("Ekleme İşlemi");
                    TextView tvSonuc = new TextView(this);
                    tvSonuc.setText("BAŞARILI");
                    dialog.setContentView(tvSonuc);
                    dialog.show();
                }
                break;

            case R.id.bGetir:

                Intent intent = new Intent(MainActivity.this,TumKayitlar.class);
               // Intent i = new Intent("guideapplication.begi.com.guideapplication.TUMKAYITLAR");
                startActivity(intent);
                break;

            case R.id.bGuncelle:
                String adGuncelle = eIsim.getText().toString();
                String telGuncelle = eTelefon.getText().toString();
                String idGuncelle = eGetir.getText().toString();

                long guncellenecekID = Long.parseLong(idGuncelle);

                VeriTabani dbGuncelle = new VeriTabani(MainActivity.this);
                try {
                    dbGuncelle.baglantiyiAc();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dbGuncelle.kaydiGuncelle(guncellenecekID, adGuncelle, telGuncelle);
                dbGuncelle.baglantiyiKapat();
                break;

            case R.id.bSil:

                String silinecekKayit = eGetir.getText().toString();
                long silinecekID = Long.parseLong(silinecekKayit);
                VeriTabani dbSil = new VeriTabani(MainActivity.this);

                try {
                    dbSil.baglantiyiAc();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                dbSil.kaydiSil(silinecekID);
                dbSil.baglantiyiKapat();

                break;

            case R.id.bKisiGetir:

                String id = eGetir.getText().toString();
                long aranacakID = Long.parseLong(id);
                VeriTabani db = new VeriTabani(MainActivity.this);

                try {
                    db.baglantiyiAc();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String eldeEdilenIsim = db.getName(aranacakID);
                String eldeEdilenYas = db.getTel(aranacakID);

                db.baglantiyiKapat();

                eIsim.setText(eldeEdilenIsim);
                eTelefon.setText(eldeEdilenYas);

                break;
        }
    }
}

