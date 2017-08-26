package com.thiagoaranha.booksapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;
import com.thiagoaranha.booksapp.dao.BookDao;
import com.thiagoaranha.booksapp.model.Book;

import java.util.Calendar;


public class BookFragment extends Fragment {

    private String id, title, author, description, image_url;

    private TextView txtTitle, txtAuthor, txtDescription;
    private ImageView imgCover;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_book, container, false);

        final Book book = getArguments().getParcelable("book");

        txtTitle = root.findViewById(R.id.title);
        txtAuthor = root.findViewById(R.id.author);
        txtDescription = root.findViewById(R.id.description);
        imgCover = root.findViewById(R.id.cover);

        if(book != null){
            id = stripHtml(book.getId());
            txtTitle.setText(stripHtml(book.getVolumeInfo().getTitle()));
            txtAuthor.setText(stripHtml(book.getVolumeInfo().getAuthors()[0]));
            txtDescription.setText(stripHtml(book.getVolumeInfo().getDescription()));

            Picasso.with(getContext()).load(book.getVolumeInfo().getImageLinks().getSmallThumbnail()).into(imgCover);

        }

        root.findViewById(R.id.btnSetAlarm).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setAlarme(book.getId());
            }
        });

        root.findViewById(R.id.btnFavorite).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                BookDao bookDao = new BookDao(getContext());
                bookDao.insert(book);
            }
        });

        return  root;
    }

    private void setAlarme(final String bookId){
        TimePickerDialog.OnTimeSetListener listener =
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                        intent.putExtra("bookId", bookId);

                        PendingIntent pIt = PendingIntent.getBroadcast(
                                getActivity(), 0, intent, 0);

                        AlarmManager alarmManager = (AlarmManager)
                                getActivity().getSystemService(Context.ALARM_SERVICE);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);

                        alarmManager.set(
                                AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(),
                                pIt);
                    }
                };
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(
                getActivity(),
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

    public String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }

}
