package tintintti.madonruoka.data;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Class stores the feeding info of one entry
 */
public class Entry implements Serializable, Comparable<Entry> {
    private long id;
    private String date;
    private String foodItem;
    private double amount;
    private String extra;
    private boolean ate;
    private String unitOfMeasure;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public boolean isAte() {
        return ate;
    }

    public void setAte(boolean ate) {
        this.ate = ate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnitOfMeasure(String unit) { this.unitOfMeasure = unit; }

    public String getUnitOfMeasure() { return this.unitOfMeasure; }

    @Override
    public String toString() {
        String a = "";
        if (amount == Math.round(amount)) {
            a = "" + Math.round(amount);
        } else {
            a = "" + amount;
        }

        return date + " " + " " + a + unitOfMeasure + " " + foodItem;
    }


    @Override
    public int compareTo(Entry another) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date d  = sdf.parse(date, new ParsePosition(0));
        Date d2 = sdf.parse(another.date, new ParsePosition(0));

        return d.compareTo(d2);
    }
}
