/*
 *
 * Copyright (c) 2001-2007 RagingCat Project.
 * LGPL License.
 * http://code.google.com/p/keytool-iui/
 *
 * This software is the confidential and proprietary information of RagingCat Project.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of RagingCat Project's license agreement.
 *
 * THE SOFTWARE IS PROVIDED AND LICENSED "AS IS" WITHOUT WARRANTY OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. 
 *
 * LICENSE FOR THE SOFTWARE DOES NOT INCLUDE ANY CONSIDERATION FOR ASSUMPTION OF RISK
 * BY KEYTOOL IUI PROJECT, AND KEYTOOL IUI PROJECT DISCLAIMS ANY AND ALL LIABILITY FOR INCIDENTAL
 * OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR OPERATION OF OR INABILITY
 * TO USE THE SOFTWARE, EVEN IF KEYTOOL IUI PROJECT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. 
 *
 */
 
 
package com.google.code.p.keytooliui.ktl.swing.panel;

/**
    a panel that displays, from left to right:
    
    . 1 buttonSelect
    . 1 buttonClearSelection
    . 1 textfieldSelection
    
    textfield is not editable

     sigfile selection
**/

import com.google.code.p.keytooliui.shared.swing.panel.*;
import com.google.code.p.keytooliui.shared.swing.dialog.*;
import com.google.code.p.keytooliui.shared.swing.optionpane.*;
import com.google.code.p.keytooliui.shared.lang.*;
import com.google.code.p.keytooliui.shared.swing.button.*;
import com.google.code.p.keytooliui.shared.swing.textfield.*;

import java.awt.*;

final public class PSelBtnTfdStrXlsSigfile extends PSelBtnTfdStrXlsAbs
{
    // -------------------
    // FINAL STATIC PUBLIC
    
    final static public String f_s_strDocPropVal = "select_basename_sigfile";
    
    // --------------------
    // FINAL STATIC PRIVATE
    
    final static private String _f_s_strLabel = "\"Sigfile\" base name:";
    
    // ------
    // PUBLIC
    
    public PSelBtnTfdStrXlsSigfile(
        javax.swing.event.DocumentListener docListenerParent,
        Frame frmParent, 
        String strTitleAppli)
    {
        super(
            docListenerParent,
            frmParent, 
            strTitleAppli, 
            PSelBtnTfdStrXlsSigfile._f_s_strLabel,
            new TF10x20SelString(),
            PSelBtnTfdStrXlsSigfile.f_s_strDocPropVal, 
            false // blnFieldRequired
            );
    }
    
    // ---------
    // PROTECTED
    
    /**
        called by superclass
    **/
    protected boolean _validateText_(String str)
    {
        String strMethod = "_validateText_(str)";
        
        if (str == null)
        {
            MySystem.s_printOutError(this, strMethod, "nil strText");
            return false;
        }
        
        /*if (str.indexOf(' ') != -1)
        {
            MySystem.s_printOutWarning(this, strMethod, "str.indexOf(' ') != -1");
                    
            String strBody = "No spaces allowed in this string value.";
            OPAbstract.s_showDialogWarning(super._frmParent_, super._strTitleAppli_, strBody);
            return true;
        }*/
                
                
        
        if (! com.google.code.p.keytooliui.ktl.util.filter.StringFilterUI.s_isAllowedSigfile(str))
        {
             MySystem.s_printOutWarning(this, strMethod, "failed");
             
            String strBody = "SigFile base name value not allowed:";
            strBody += "\n";
            strBody += "  ";
            strBody += "\"";
            strBody += str;
            strBody += "\"";
                    
            strBody += "\n\n";
            strBody += com.google.code.p.keytooliui.ktl.util.filter.StringFilterUI.s_getRuleSigfile();
                    
            OPAbstract.s_showDialogWarning(super._frmParent_, super._strTitleAppli_, strBody);
            return true;
        }
        

        super._tfdCurSelection_.setText(str);
        super._setSelectedValue_(true);
        super._btnClearSelection_.setEnabled(true);
        
        // ending
        return true;
    }
}