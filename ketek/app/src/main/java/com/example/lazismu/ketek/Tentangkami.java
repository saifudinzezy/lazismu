package com.example.lazismu.ketek;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Tentangkami extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentangkami);

        final ListView lvItems = (ListView) findViewById(R.id.lv_items);
        ExpandableAdapter adapter = getAdapter();

        lvItems.setAdapter(adapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpandableAdapter adapter = (ExpandableAdapter) parent.getAdapter();

                Item item = (Item) adapter.getItem(position);
                if (item != null) {
                    if (item.isExpanded) {
                        item.isExpanded = false;
                    } else {
                        item.isExpanded = true;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private String list[] =
            {"Latar Belakang", "Visi & Misi", "Kebijakan Strategis", "Kontak Kami"};
    private String list1[] =
            {"LAZISMU adalah lembaga zakat tingkat nasional yang berkhidmat dalam pemberdayaan masyarakat melalui pendayagunaan secara produktif dana zakat, infaq, wakaf dan dana kedermawanan lainnya baik dari perseorangan, lembaga, perusahaan dan instansi lainnya.\n" +
                    "\n" +
                    "Didirikan oleh PP. Muhammadiyah pada tahun 2002, selanjutnya dikukuhkan oleh Menteri Agama Republik Indonesia sebagai Lembaga Amil Zakat Nasional melalui SK No. 457/21 November 2002. Dengan telah berlakunya Undang-undang Zakat nomor 23 tahun 2011, Peraturan Pemerintah nomor 14 tahun 2014, dan Keputusan Mentri Agama Republik Indonesia nomor 333 tahun 2015. LAZISMU sebagai lembaga amil zakat nasional telah dikukuhkan kembali melalui SK Mentri Agama Republik Indonesia nomor 730 tahun 2016.\n" +
                    "\n" +
                    "Latar belakang berdirinya LAZISMU terdiri atas dua faktor. Pertama, fakta Indonesia yang berselimut dengan kemiskinan yang masih meluas, kebodohan dan indeks pembangunan manusia yang sangat rendah. Semuanya berakibat dan sekaligus disebabkan tatanan keadilan sosial yang lemah.\n" +
                    "\n" +
                    "Kedua, zakat diyakini mampu bersumbangsih dalam mendorong keadilan sosial, pembangunan manusia dan mampu mengentaskan kemiskinan. Sebagai negara berpenduduk muslim terbesar di dunia, Indonesia memiliki potensi zakat, infaq dan wakaf yang terbilang cukup tinggi. Namun, potensi yang ada belum dapat dikelola dan didayagunakan secara maksimal sehingga tidak memberi dampak yang signifikan bagi penyelesaian persoalan yang ada.\n" +
                    "\n" +
                    "Berdirinya LAZISMU dimaksudkan sebagai institusi pengelola zakat dengan manajemen modern yang dapat menghantarkan zakat menjadi bagian dari penyelesai masalah (problem solver) sosial masyarakat yang terus berkembang.\n" +
                    "\n" +
                    "Dengan budaya kerja amanah, professional dan transparan, LAZISMU berusaha mengembangkan diri menjadi Lembaga Zakat terpercaya. Dan seiring waktu, kepercayaan publik semakin menguat.\n" +
                    "\n" +
                    "Dengan spirit kreatifitas dan inovasi, LAZISMU senantiasa menproduksi program-program pendayagunaan yang mampu menjawab tantangan perubahan dan problem sosial masyarakat yang berkembang.\n" +
                    "\n" +
                    "Saat ini, LAZISMU telah tersebar hampir di seluruh Indonesia yang menjadikan program-program pendayagunaan mampu menjangkau seluruh wilayah secara cepat, fokus dan tepat sasaran.", "VISI\n" +
                    "\n" +
                    "Menjadi Lembaga Amil Zakat Terpercaya\n" +
                    "\n" +
                    "MISI\n" +
                    "\n" +
                    "1. Optimalisasi pengelolaan ZIS yang amanah, profesional dan transparan;\n" +
                    "\n" +
                    "2. Optimalisasi pendayagunaan ZIS yang kreatif, inovatif dan produktif;\n" +
                    "\n" +
                    "3. Optimalisasi pelayanan donatur","Misi Pendayagunaan :\n" +
                    "\n" +
                    "Terciptanya kehidupan sosial ekonomi umat yang berkualitas sebagai benteng atas problem kemiskinan, keterbelakangan, dan kebodohan pada masyarakat melalui berbagai program yang dikembangkan Muhammadiyah.\n" +
                    "\n" +
                    "Kebijakan Strategis Pendayagunaan:\n" +
                    "\n" +
                    "1.\tPrioritas penerima manfaat adalah kelompok fakir, miskin dan fisabilillah.\n" +
                    "\n" +
                    "2.\tPendistribusian ZIS dilakukan secara terprogram (terencana dan terukur) sesuai core gerakan Muhammadiyah, yakni: pendidikan, ekonomi, dan sosial-dakwah.\n" +
                    "\n" +
                    "3.\tMelakukan sinergi dengan majelis, lembaga, ortom dan amal-usaha Muhammdiyah dalam merealisasikan program.\n" +
                    "\n" +
                    "4.\tMelakukan sinergi dengan institusi dan komunitas diluar Muhammadiyah untuk memperluas domain dakwah sekaligus meningkatkan awareness public kepada persyarikatan.\n" +
                    "\n" +
                    "5.\tMeminimalisir bantuan karitas kecuali bersifat darurat seperti di kawasan timur Indonesia, daerah yang terpapar bencana dan upaya-upaya penyelamatan.\n" +
                    "\n" +
                    "6.\tIntermediasi bagi setiap usaha yang menciptakan kondisi dan faktor-faktor pendukung bagi terwujudnya masyarakat Islam yang sebenar-benarnya. [Visi Muhammadiyah 2025\n" +
                    "\n" +
                    "7.\tMemobilisasi pelembagaan gerakan ZIS di seluruh struktur Muhammadiyah dan amal usaha.\n" +
                    "\n" +
                    "Sinergi Pendayagunaan\n" +
                    "\n" +
                    "Berpijak pada posisi LAZISMU sebagai lembaga intermediate, maka dalam penyaluran dan pendayagunaan dana ziswaf bersinergi dengan berbagai lembaga baik di internal Muhammadiyah maupun lembaga diluar Muhammadiyah.\n" +
                    "\n" +
                    "Seperti program pendayagunaan bidang pertanian, lazismu bersinergi dengan MPM ( Majelis Pemberdayaan Masyarakat) PP Muhammadiyah, program kemanusiaan bersinergi dengan LPB PP Muhammadiyah, masalah sosial bersinergi dengan MPS Muhammadiyah, bidang ekonomi dengan MEK Muhammadiyah dan untuk pemberdayaan kaum perempuan lazismu bersinergi dengan PP ‘Aisyiyah.\n" +
                    "\n" +
                    "Sedang sinergi dengan lembaga di luar Muhammadiyah, LAZISMU telah menggandeng berbagai lembaga dan komunitas dalam menyalurkan dan mendayagunakan dana ziswaf seperti lembaga IWAPI, komunitas WIRAMUDA, berbagai komunitas hobby dan profesi dan sebagainya.\n" +
                    "\n" +
                    "Tujuan dari sinergi adalah agar pendayagunaan memberi manfaat yang maksimal kepada masyarakat karena dikelola oleh lembaga pengelola yang expert serta menjangkau lokasi sasaran program yang lebih luas.", "085702055501 \n tayo@gmail.com"};

    private ExpandableAdapter getAdapter(){

        List<Item> items = new ArrayList<>();

        for(int i = 0; i < list.length; i++){
            Item item = new Item();
//            item.title = "Title Item ";
            item.title = list[i];
//            item.desciption = "Description for Title Item "+ i;
            item.desciption = list1[i];
            item.isExpanded = false;

            items.add(item);
        }

        return new ExpandableAdapter(this, items);
    }



}
