/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Dimension;
import javax.swing.JDialog;
import model.Ogrenci;

/**
 *
 * @author mericgerceker
 */
public class GuncelleDialogYonetici {
    private Ogrenci ogrenci;
    private JDialog dialog;
    public GuncelleDialogYonetici(Ogrenci ogrenci, JDialog dialog){
        this.ogrenci = ogrenci;
        this.dialog = dialog;
    }
    
    public void dialogGoster(){
        dialog.setMinimumSize(new Dimension(400, 400));
        dialog.setVisible(true);
    }
    
    public void dialogKapat(){
        dialog.dispose();
    }
}
