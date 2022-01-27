package DataProje1;

public class Iris implements Comparable<Iris> {

    private double sepal_length;
    private double sepal_width;
    private double petal_length;
    private double petal_width;
    private String iris_class;
    private double distance; //sogulanan çiçeğe ait uzaklığı tutar.

    public Iris(double sepal_length, double sepal_width, double petal_length, double petal_width, String iris_class) {
        this.sepal_length = sepal_length;
        this.sepal_width = sepal_width;
        this.petal_length = petal_length;
        this.petal_width = petal_width;
        this.iris_class = iris_class;
    }

    public Iris(Iris iris){
        this.sepal_length = iris.getSepal_length();
        this.sepal_width = iris.getSepal_width();
        this.petal_length = iris.getPetal_length();
        this.petal_width = iris.getPetal_width();
        this.iris_class = iris.getIris_class();
    }

    public double getSepal_length() {
        return sepal_length;
    }

    public void setSepal_length(double sepal_length) {
        this.sepal_length = sepal_length;
    }

    public double getSepal_width() {
        return sepal_width;
    }

    public void setSepal_width(double sepal_width) {
        this.sepal_width = sepal_width;
    }

    public double getPetal_length() {
        return petal_length;
    }

    public void setPetal_length(double petal_length) {
        this.petal_length = petal_length;
    }

    public double getPetal_width() {
        return petal_width;
    }

    public void setPetal_width(double petal_width) {
        this.petal_width = petal_width;
    }

    public String getIris_class() {
        return iris_class;
    }

    public void setIris_class(String iris_class) {
        this.iris_class = iris_class;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return   sepal_length + "\t\t" + sepal_width +
                "\t\t" + petal_length + "\t\t" + petal_width +
                "\t\t" + iris_class  + "\t\t" + distance ;
    }

    //Veriler Iris tipindeki Arraylist'e atılıp, sorgulanan çiçeğe uzaklıkları (distance) hesaplandıktan sonra
    //bu uzaklıklara göre küçükten büyüğe doğru sıralanmaları amacıyla kullanılır.
    public int compareTo(Iris i) {
        if (this.getDistance() < i.getDistance()){
            return -1;
        } else if (i.getDistance() < this.getDistance()) {
            return 1;
        }
        return 0;
    }

    //Kullanıcıdan alınan, çiçeğe ait 4 özelliği parametre olarak alır ve formüle uygun şekilde
    //sorgulanan çiçeğe olan uzaklığı hesaplar.
    public void findDistance(double sepal_length, double sepal_width, double petal_length, double petal_width) {
        double sum = Math.pow((this.sepal_length - sepal_length), 2) +
                Math.pow((this.sepal_width - sepal_width), 2) +
                Math.pow((this.petal_length - petal_length), 2) +
                Math.pow((this.petal_width - petal_width), 2);
        this.distance = Math.sqrt(sum);
    }

    //Kullanıcıdan alınan, çanak yapraklara ait özellikleri parametre olarak alır ve formüle
    //uygun şekilde sorgulanan çiçeğe olan uzaklığı hesaplar.
    public void findSepalDistance(double sepal_length, double sepal_width) {
        double sum = Math.pow((this.sepal_length - sepal_length), 2) +
                    Math.pow((this.sepal_width - sepal_width), 2) ;
        this.distance = Math.sqrt(sum);
    }

    //Kullanıcıdan alınan, taç yapraklara ait özellikleri parametre olarak alır ve formüle
    //uygun şekilde sorgulanan çiçeğe olan uzaklığı hesaplar.
    public void findPetalDistance(double petal_length, double petal_width) {
        double sum = Math.pow((this.petal_length - petal_length), 2) +
                    Math.pow((this.petal_width - petal_width), 2);
        this.distance = Math.sqrt(sum);
    }
}