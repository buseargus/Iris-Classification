package DataProje1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class IrisClassification extends JFrame implements ActionListener {

    public static int HEIGHT = 600; //Arayüzün yüksekliğini tutar.
    public static int WIDTH = 1000; //Arayüzün genişliğini tutar.
    public static JTextArea text;
    public static ArrayList<Iris> iris_data = new ArrayList<>(); //Dosyadan alınan veri setindeki bilgileri tutar.
    public static String help; //Programın kullanılışı hakkında bilgi içerir.

    public static void main(String[] args) {
        readFileData();
        IrisClassification ic = new IrisClassification();
        ic.setVisible(true);
    }

    //Constructorda arayüze ai           t görsel özellikler, butonlar, paneller oluşturulur.
    //Her birine ActionListener eklenir.
    public IrisClassification() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        text = new JTextArea();
        add(text, BorderLayout.CENTER);
        help = "Tüm özelliklere göre çiçek sınıfı bulmak için özellikleri \"çanak yaprak uzunluğu, çanak yaprak genişliği," +
                "taç yaprak uzunluğu, taç yaprak genişliği, k sayısı\" sırasıyla ve virgülle ayrılmış olarak girerek \n\"" +
                "Tüm özelliklere göre\" butonuna basınız.\n Çanak yaprak özelliklerine göre çiçek sınıfı bulmak için özellikleri" +
                "\"çanak yağrak uzunluğu, çanak yaprak genişliği, k sayısı\" sırasıyla ve virgülle ayrılmış olarak girerek\n" +
                "\"Çanak yapraklara göre\" butonuna basınız. \n Taç yaprak özelliklerine göre çiçek sınıfı bulmak için özellikleri" +
                "\"Taç yaprak uzunluğu, taç yaprak genişliği, k sayısı\" sırasıyla ve virgülle ayrılmış olarak girerek " +
                "\n\"Taç yapraklara göre\" butonuna basınız." +
                "\nVeri setine yeni çiçek eklemek için özellikleri \"çanak yaprak uzunluğu, çanak yaprak genişliği, " +
                "taç yaprak uzunluğu, taç yaprak genişliği, çiçek sınıfı\" \nsırasıyla ve virgülle ayrılmış olarak girerek " +
                "\"Çiçek ekle\" butonuna basınız." +
                "\nVeri setinden çiçek silmek için silmek istediğiniz çiçeğin tüm veriler arasındaki sırasını yazıp" +
                "\"Çiçek sil\" butonuna basınız." +
                "\nBaşarı oranı ölçmek için k değeri girip \"Başarı oranı\" butonuna basınız.";
        text.setText(help);
        JScrollPane pane = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(pane);
        JPanel panel = new JPanel();
        JPanel findPanel = new JPanel();
        panel.setLayout(new FlowLayout());
        findPanel.setLayout(new FlowLayout());
        JLabel label = new JLabel("Sınıf bul: ");
        findPanel.add(label);
        JButton find = new JButton("Tüm özelliklere göre");
        find.addActionListener(this);
        findPanel.add(find);
        JButton find_by_sepal = new JButton("Çanak yapraklara göre");
        find_by_sepal.addActionListener(this);
        findPanel.add(find_by_sepal);
        JButton find_by_petal = new JButton("Taç yapraklara göre");
        find_by_petal.addActionListener(this);
        findPanel.add(find_by_petal);
        JButton help = new JButton("Yardım");
        help.addActionListener(this);
        panel.add(help);
        JButton clear = new JButton("Ekranı temizle");
        clear.addActionListener(this);
        panel.add(clear);
        JButton add = new JButton("Çiçek ekle");
        add.addActionListener(this);
        panel.add(add);
        JButton delete = new JButton("Çiçek sil");
        delete.addActionListener(this);
        panel.add(delete);
        JButton deleteAll = new JButton("Tüm verileri sil");
        deleteAll.addActionListener(this);
        panel.add(deleteAll);
        JButton success_rate = new JButton("Başarı oranı");
        success_rate.addActionListener(this);
        panel.add(success_rate);
        JButton list = new JButton("Verileri listele");
        list.addActionListener(this);
        panel.add(list);
        JButton quit = new JButton("Çıkış");
        quit.addActionListener(this);
        panel.add(quit);
        add(panel, BorderLayout.NORTH);
        add(findPanel, BorderLayout.SOUTH);
    }

    //ActionListener class'ına ait abstract bir metottur.
    //Hangi butona basıldığında hangi işlemlerin gerçekleştirileceğini belirler.
    public void actionPerformed(ActionEvent e ) {
        String action = e.getActionCommand();
        //Bu buton çiçeğin tüm özellikleri kullanılarak tahmin yapılmasını sağlar.
        //Özelliklerin girilme sırası programda belirtilmiştir.
        if (action.equals("Tüm özelliklere göre")) {
            try {
                String data = text.getText();
                StringTokenizer tokenizer = new StringTokenizer(data, ",");
                double sepal_length = Double.parseDouble(tokenizer.nextToken().trim());
                double sepal_width = Double.parseDouble(tokenizer.nextToken().trim());
                double petal_length = Double.parseDouble(tokenizer.nextToken().trim());
                double petal_width = Double.parseDouble(tokenizer.nextToken().trim());
                int k_value = Integer.parseInt(tokenizer.nextToken().trim());
                ArrayList<Iris> iris_data_copy = new ArrayList<>();
                for(Iris iris : iris_data) {
                    iris_data_copy.add(new Iris(iris));
                }
                for (int i = 0; i < iris_data_copy.size(); i++) {
                    iris_data_copy.get(i).findDistance(sepal_length, sepal_width, petal_length, petal_width);
                }
                Collections.sort(iris_data_copy);
                text.setText(printIris(k_value, iris_data_copy));
            } catch(Exception e2 ) {
                text.setText("Hatalı veri girişi.");
            }
            //Bu buton çiçeğin çanak yaprak özellikleri kullanılarak tahmin yapılmasını sağlar.
            //Özelliklerin girilme sırası programda belirtilmiştir.
        } else if (action.equals("Çanak yapraklara göre")){
            try {
                String data = text.getText();
                StringTokenizer tokenizer = new StringTokenizer(data, ",");
                double sepal_length = Double.parseDouble(tokenizer.nextToken().trim());
                double sepal_width = Double.parseDouble(tokenizer.nextToken().trim());
                int k_value = Integer.parseInt(tokenizer.nextToken().trim());
                ArrayList<Iris> iris_data_copy = new ArrayList<>();
                for(Iris iris : iris_data) {
                    iris_data_copy.add(new Iris(iris));
                }
                for (int i = 0; i < iris_data_copy.size(); i++) {
                    iris_data_copy.get(i).findSepalDistance(sepal_length, sepal_width);
                }
                Collections.sort(iris_data_copy);
                text.setText(printIris(k_value, iris_data_copy));
            } catch(Exception e3) {
                text.setText("Hatalı veri girişi");
            }
            //Bu buton çiçeğin taç yaprak özellikleri kullanılarak tahmin yapılmasını sağlar.
            //Özelliklerin girilme sırası programda belirtilmiştir.
        } else if(action.equals("Taç yapraklara göre")){
            try {
                String data = text.getText();
                StringTokenizer tokenizer = new StringTokenizer(data, ",");
                double petal_length = Double.parseDouble(tokenizer.nextToken().trim());
                double petal_width = Double.parseDouble(tokenizer.nextToken().trim());
                int k_value = Integer.parseInt(tokenizer.nextToken().trim());
                ArrayList<Iris> iris_data_copy = new ArrayList<>();
                for(Iris iris : iris_data) {
                    iris_data_copy.add(new Iris(iris));
                }
                for (int i = 0; i < iris_data_copy.size(); i++) {
                    iris_data_copy.get(i).findPetalDistance(petal_length, petal_width);
                }
                Collections.sort(iris_data_copy);
                text.setText(printIris(k_value, iris_data_copy));
            } catch(Exception e3) {
                text.setText("Hatalı veri girişi.");
            }
            //Ekrandaki metinlerin silinmesini sağlar.
        } else if (action.equals("Ekranı temizle")) {
            text.setText("");
            //Bellekteki veri setine yeni çiçek eklenmesini sağlar.
            //Özelliklerin girilme sırası programda belirtilmiştir.
        } else if (action.equals("Çiçek ekle")) {
            String data = text.getText();
            StringTokenizer tokenizer = new StringTokenizer(data, ",");
            double sepal_length = Double.parseDouble(tokenizer.nextToken().trim());
            double sepal_width = Double.parseDouble(tokenizer.nextToken().trim());
            double petal_length = Double.parseDouble(tokenizer.nextToken().trim());
            double petal_width = Double.parseDouble(tokenizer.nextToken().trim());
            String iris_class = tokenizer.nextToken().trim();
            iris_data.add(new Iris(sepal_length, sepal_width, petal_length, petal_width, iris_class));
            //Bellekteki veri setinden indexe göre çiçek silinmesini sağlar.
        } else if (action.equals("Çiçek sil")) {
            try {
                int index = Integer.parseInt(text.getText().trim());
                iris_data.remove(index - 1);
            } catch (Exception e3) {
                text.setText("Hatalı veri girişi");
            }
            //Bellekteki tüm verinin silinmesini sağlar.
        } else if (action.equals("Tüm verileri sil")){
            iris_data.clear();
            //Bellekteki çiçek türlerinin her birinden 10'ar tane ayrılarak geri kalanların bu çiçeklerle
            //test edilmesini sağlar. Butona basılmadan önce k değeri kullanıcı tarafından ekrana yazılmalıdır.
        } else if (action.equals("Başarı oranı")) {
            try {
                int k_value = Integer.parseInt(text.getText());
                ArrayList<Iris> iris_train = new ArrayList<>();
                ArrayList<Iris> iris_test = new ArrayList<>();
                for (int i = 40; i < 50; i++) {
                    iris_test.add(iris_data.get(i)); iris_test.add(iris_data.get(i + 50)); iris_test.add(iris_data.get(i + 100));
                    iris_train.add(iris_data.get(i - 40)); iris_train.add(iris_data.get(i + 10)); iris_train.add(iris_data.get(i + 60));
                }
                ArrayList<Iris> iris_data_copy = new ArrayList<>();
                for(Iris iris : iris_train) {
                    iris_data_copy.add(new Iris(iris));
                }
                int success_count = 0;
                String s = "";
                for (Iris iris : iris_test) {
                    for (int i = 0; i < iris_data_copy.size(); i++) {
                        iris_data_copy.get(i).findDistance(iris.getSepal_length(), iris.getSepal_width(),
                                iris.getPetal_length(), iris.getPetal_width());
                    }
                    Collections.sort(iris_data_copy);
                    s += printIris(k_value, iris_data_copy);
                    String iris_class = iris.getIris_class();
                    String predicted_class = findClass(k_value, iris_data_copy);
                    if (iris_class.equals(predicted_class)) {
                        success_count += 1;
                    }
                    s += "\nÇiçeğin gerçek türü : " + iris_class + "\n";
                }
                s += "Başarı oranı : " + ((double) success_count / iris_test.size());
                text.setText(s);
            } catch (Exception e3) {
                text.setText("Hatalı veri.");
            }
            //Bellekteki tüm çiçeklerin ekrana listelenmesini sağlar.
        } else if (action.equals("Verileri listele")) {
            try {
                String s = "Çanak yaprak uzunluğu" + "\t" + "Çanak yaprak genişliği" + "\t" +
                        "Taç yaprak uzunluğu" + "\t" + "Taç yaprak genişliği" + "\t" +
                        "Sınıf" + "\t" + "Sınıfı aranan çiçeğe uzaklık" + "\n";
                for (int i = 0; i < iris_data.size(); i++) {
                    s += iris_data.get(i).toString() + "\n";
                }
                text.setText(s);
            } catch(Exception e3) {
                text.setText("Veri bulunamadı.");
            }
            //Programdan çıkış yapılmasını sağlar.
        } else if (action.equals("Çıkış")) {
            System.exit(0);
            //Programın nasıl kullanılacağıyla ilgili yardım metninin ekrana yazdırılmasını sağlar.
        } else if (action.equals("Yardım")) {
            text.setText(help);
        }
    }

    //Bu metodun amacı çiçeklere ait özellik bilgisini, yer aldıkları text dosyasından
    //ArrayList'e yerleştirmektir.
    public static void readFileData() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream("iris.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı.");
            System.exit(0);
        }
        while(scanner.hasNext()) {
            String data = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(data, ",");
            double sepal_length = Double.parseDouble(tokenizer.nextToken());
            double sepal_width = Double.parseDouble(tokenizer.nextToken());
            double petal_length = Double.parseDouble(tokenizer.nextToken());
            double petal_width = Double.parseDouble(tokenizer.nextToken());
            String iris_class = tokenizer.nextToken();
            iris_data.add(new Iris(sepal_length, sepal_width, petal_length, petal_width, iris_class));
        }
    }

    //Bu metodun amacı; sorgulanan çiçeğe olan uzaklıklarına (distance) göre sıralanmış
    //Arraylist'teki en yakın k çiçeğin türlerine göre sınıf tahmini gerçekleştirmektir.
    public static String findClass(int k_value, ArrayList<Iris> iris_data) {
        ArrayList<String> nearestNeighbors = new ArrayList<>();
        for(int i = 0; i < k_value; i++) {
            nearestNeighbors.add(iris_data.get(i).getIris_class());
        }
        int setosa = Collections.frequency(nearestNeighbors, "Iris-setosa");
        int versicolor = Collections.frequency(nearestNeighbors, "Iris-versicolor");
        int virginica = Collections.frequency(nearestNeighbors, "Iris-virginica");
        if((setosa > versicolor) && (setosa > virginica)) {
            return "Iris-setosa";
        } else if ((versicolor > setosa) && (versicolor > virginica)) {
            return "Iris-versicolor";
        } else if ((virginica > setosa) && (virginica > versicolor)) {
            return "Iris-virginica";
        }
        return nearestNeighbors.get(0);
    }

    //Bu metot sorgulanan çiçeğe en yakın k adet çiçeğin bilgilerini String içinde tutar,
    //daha sonra çiçeğin türünü de findClass() metodu ile tahminleyerek bu String'e ekler
    //ve döndürür. Daha sonra bu String textArea'da ekrana yazdırılır.
    public static String printIris(int k_value, ArrayList<Iris> iris_data_copy) {
        String s = "\nEn yakın " + k_value + " adet bitkinin özellikleri:\n"  +
                "\tÇanak yaprak uzunluğu\t" + "Çanak yaprak genişliği\t" +
                "Taç yaprak uzunluğu\t" + "Taç yaprak genişliği\t" +
                "Sınıf\t\t" + "Sınıfı aranan çiçeğe uzaklık\n";
        for (int j = 0; j < k_value; j++) {
            s += (j+1) + "\t" + iris_data_copy.get(j).toString() + "\n";
        }
        s += "Çiçeğin tahmini türü : " + findClass(k_value, iris_data_copy);
        return s;
    }
}