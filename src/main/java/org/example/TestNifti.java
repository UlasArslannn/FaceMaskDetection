package org.example;



import ij.ImagePlus;
import ij.IJ;

public class TestNifti {
    public static void main(String[] args) {
        String filePath = "C:\\adata\\imageJ\\M_T1_t1_se_tra";
        ImagePlus image = IJ.openImage(filePath);
        if (image != null) {
            image.show(); // Görüntüyü göster
        } else {
            System.out.println("Dosya açılamadı.");
        }
    }
}