package guideapplicaiton.begi.com.guideapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by asus1 on 10.10.2017.
 */
public class VeriTabani {

    private static final String DATABASE_ISIM = "Kisiler";
    private static final String DATABASE_TABLO= "Rehber";
    private static final int DATABASE_VERSION= 1;

    private final Context contextim;
    private VeritabaniHelper veritabanihelper;
    private SQLiteDatabase veritabanim;

    public static final String KEY_ROW_ID ="_id";
    public static final String KEY_ISIM = "isim";
    public static final String KEY_TELEFON="telefon";

    public VeriTabani(Context c) {
        this.contextim = c;
    }

    public VeriTabani baglantiyiAc() throws SQLException {

        veritabanihelper = new VeritabaniHelper(contextim);
        veritabanim = veritabanihelper.getWritableDatabase();

        return this;
    }

    public void baglantiyiKapat() {

        veritabanihelper.close();
    }

    private static class VeritabaniHelper extends SQLiteOpenHelper {

        public VeritabaniHelper(Context contextim) {
            super(contextim, DATABASE_ISIM, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL("CREATE TABLE " + DATABASE_TABLO + " (" + KEY_ROW_ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT , " + KEY_ISIM
                    + " TEXT NOT NULL, " + KEY_TELEFON + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLO);
            onCreate(db);
        }
    }

    public long isimTelBilgisiniKaydet(String ad, String telefon) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();

        cv.put(KEY_ISIM, ad);
        cv.put(KEY_TELEFON, telefon);

        return veritabanim.insert(DATABASE_TABLO, null, cv);
    }

    public String tumKayitlar() {
        // TODO Auto-generated method stub

        String[] sutunlar = new String[] { KEY_ROW_ID, KEY_ISIM, KEY_TELEFON };
        Cursor c = veritabanim.query(DATABASE_TABLO, sutunlar, null, null,
                null, null, null);

        String tumKayitlar = "";

//sütunların id'leri değişkenlere atandı
//id sütunu 0, isim sütunu 1 ve telefon sütünu 2 indexlerine sahip
        int idSiraNo = c.getColumnIndex(KEY_ROW_ID);
        int isimSiraNo = c.getColumnIndex(KEY_ISIM);
        int telSiraNo = c.getColumnIndex(KEY_TELEFON);

//tüm kayıtların okunması bu for döngüsüyle sağlandı
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            tumKayitlar = tumKayitlar + c.getString(idSiraNo) + "    "
                    + c.getString(isimSiraNo) + "  " + c.getString(telSiraNo)
                    + " \n";
        }
        return tumKayitlar;
    }

    public String getName(long aranacakID) {
        // TODO Auto-generated method stub
        String[] sutunlar = new String[] { KEY_ROW_ID, KEY_ISIM, KEY_TELEFON };
        Cursor c = veritabanim.query(DATABASE_TABLO, sutunlar, KEY_ROW_ID + "="
                + aranacakID, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
            String isim = c.getString(1);
            return isim;
        }
        return null;
    }

    public String getTel(long aranacakID) {
        // TODO Auto-generated method stub
        String[] sutunlar = new String[] { KEY_ROW_ID, KEY_ISIM, KEY_TELEFON };
        Cursor c = veritabanim.query(DATABASE_TABLO, sutunlar, KEY_ROW_ID + "="
                + aranacakID, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
            String tel = c.getString(2);
            return tel;
        }
        return null;
    }

    public void kaydiGuncelle(long guncellenecekID, String adGuncelle, String telGuncelle) {
        // TODO Auto-generated method stub
        ContentValues cvGuncelle = new ContentValues();

        cvGuncelle.put(KEY_ISIM, adGuncelle);
        cvGuncelle.put(KEY_TELEFON, telGuncelle);

        veritabanim.update(DATABASE_TABLO, cvGuncelle, KEY_ROW_ID + "="
                + guncellenecekID, null);
    }

    public void kaydiSil(long silinecekID) {
        // TODO Auto-generated method stub
        veritabanim .delete(DATABASE_TABLO, KEY_ROW_ID + "=" + silinecekID, null);
    }
}
