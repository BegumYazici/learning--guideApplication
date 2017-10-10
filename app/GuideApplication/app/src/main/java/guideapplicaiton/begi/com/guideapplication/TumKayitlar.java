package guideapplicaiton.begi.com.guideapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Created by asus1 on 10.10.2017.
 */
public class TumKayitlar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);

        TextView tv=(TextView)findViewById(R.id.tvTumKayitlar);
        VeriTabani db=new VeriTabani(TumKayitlar.this);

        try {
            db.baglantiyiAc();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String tumKayitlar=db.tumKayitlar();

        db.baglantiyiKapat();

        tv.setText(tumKayitlar);
    }
}
