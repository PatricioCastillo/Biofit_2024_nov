package adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.biofit.R;

public class SliderSportAdapter extends PagerAdapter {

    private Context mContext;
    private int[] nImageSport = {R.drawable.d, R.drawable.e, R.drawable.f};
    public SliderSportAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        // Solo devuelve el tamaño del arreglo de imágenes deportivas para este caso
        return nImageSport.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(nImageSport[position]); // Usar mImageDeport
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    // Método para obtener mImageIds
    public int[] getImageSports() {
        return nImageSport;
    }
}
