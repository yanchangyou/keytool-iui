/*
 *
 * Copyright (c) 2001-2011 keyTool IUI Project.
 * LGPL License.
 * http://code.google.com/p/keytool-iui/
 *
 *
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of keyTool IUI Project's license agreement.
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
 
 
package com.google.code.p.keytooliui.ktl.util.jarsigner;

/**

    KTLKprSaveNewRsaPkcs12: "Kpr" for "KeyPair"
    
    
    !!!!!!!!! not done yet: overwritting existing KeyPair entry

**/

import com.google.code.p.keytooliui.ktl.swing.dialog.*;

import com.google.code.p.keytooliui.shared.swing.optionpane.*;
import com.google.code.p.keytooliui.shared.lang.*;
import com.google.code.p.keytooliui.shared.util.jarsigner.*;

// memo: assigning full class path coz ambiguous: same class name in several Java packages
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import java.awt.*;
import java.io.*;
import java.util.*;

public class KTLKprSaveNewRsaPkcs12 extends KTLKprSaveNewRsaAbs
{
    // ------
    // PUBLIC
    
    /**
        if any error in code, exiting
        in case of trbrl: open up a warning dialog, and return false;
        
        algo:
        . get fileOpen keystore
        . open keystore
        . fill in table KeyPair
        . show dialog KeyPair new Rsa
          . get aliasKpr
          . get passwdKpr
        . create new KeyPair
        . create new certificate of type X.509
        . assign new entry to open keystore
        . save keystore
    **/
    public boolean doJob()
    {
        String strMethod = "doJob()";
        
        
        super._setEnabledCursorWait_(true);
        
        
        // ---
        
        // memo: keystore should be of type "PKCS12", provided by?  "BC", or "SunRsaSign
        File fleOpenKstPkcs12 = UtilJsrFile.s_getFileOpen(
            super._frmOwner_, super._strPathAbsKst_);
        
        if (fleOpenKstPkcs12 == null)
        {
            super._setEnabledCursorWait_(false);
            MySystem.s_printOutError(this, strMethod, "nil fleOpenKstPkcs12");
            return false;
        }
        
        // ----
        // open keystore
        
        if (super._chrsPasswdKst_ == null)
        {
            super._setEnabledCursorWait_(false);
            MySystem.s_printOutExit(this, strMethod, "nil super._chrsPasswdKst_"); 
        }
        
        KeyStore kstOpenPkcs12 = UtilKstPkcs12.s_getKeystoreOpen(
            super._frmOwner_, 
            fleOpenKstPkcs12,
            super._chrsPasswdKst_);
            

        
        if (kstOpenPkcs12 == null)
        {
            super._setEnabledCursorWait_(false);
            MySystem.s_printOutError(this, strMethod, "nil kstOpenPkcs12");
            return false;
        }
        
        if (! super._doJob_(fleOpenKstPkcs12, kstOpenPkcs12))
        {
            super._setEnabledCursorWait_(false);
            MySystem.s_printOutError(this, strMethod, "failed");
            return false;
        }
        
        super._setEnabledCursorWait_(false);
        
        // ending
        return true;
    }
    
    public KTLKprSaveNewRsaPkcs12(
        Frame frmOwner, 
   
        
        // input
        String strPathAbsOpenKst, // existing keystore of type PKCS12 
        char[] chrsPasswdOpenKst, 
        
        // output
        int intSizeKpr,
        //int intCertX509Version, // either 1 or 3 (version #1 or version #3)
        String strCertAlgoSignType, //
        
        
        int intValidityKpr,
        
        String strKprX500DN_CN,    // "DN" for "Distinguisheds names", "CN" for "Common Name"
        String strKprX500DN_OU,
        String strKprX500DN_O,
        String strKprX500DN_L,
        String strKprX500DN_ST,
        String strKprX500DN_C,
        String strKprX500DN_EMAIL,
            
        String strKprX500DNM_T, // "DN" for "Distinguished Name", "M" for "More""
        String strKprX500DNM_SN,
        String strKprX500DNM_STREET,
        String strKprX500DNM_BUSINESS_CATEGORY,
        String strKprX500DNM_POSTAL_CODE,
        String strKprX500DNM_DN_QUALIFIER,
        String strKprX500DNM_PSEUDONYM,
        String strKprX500DNM_DATE_OF_BIRTH,
        String strKprX500DNM_PLACE_OF_BIRTH,
        String strKprX500DNM_GENDER,
        String strKprX500DNM_COUNTRY_OF_CITIZENSHIP,
        String strKprX500DNM_COUNTRY_OF_RESIDENCE,
        String strKprX500DNM_NAME_AT_BIRTH,
        String strKprX500DNM_POSTAL_ADDRESS,

        String strKprX520N_SURNAME,
        String strKprX520N_GIVENNAME,
        String strKprX520N_INITIALS,
        String strKprX520N_GENERATION,
        String strKprX520N_UNIQUE_IDENTIFIER
        )
    {
        super(
            frmOwner, 
       
        
            // input
            strPathAbsOpenKst, // existing keystore of type PKCS12 
            chrsPasswdOpenKst, 
        
            // output
            //KTLAbs.f_s_strProviderKstBC, // provider for keypair generator   // eg: "SUN, "BC", "SunRsaSign", "SunRsaSign", "SunJCE", SunJGSS"
            intSizeKpr,
            //intCertX509Version, // either 1 or 3 (version #1 or version #3)
            strCertAlgoSignType, //
        
        
            intValidityKpr,
        
            strKprX500DN_CN,    // "DN" for "Distinguisheds names", "CN" for "Common Name"
            strKprX500DN_OU,
            strKprX500DN_O,
            strKprX500DN_L,
            strKprX500DN_ST,
            strKprX500DN_C,
            strKprX500DN_EMAIL,
                
            strKprX500DNM_T, // "DN" for "Distinguished Name", "M" for "More""
            strKprX500DNM_SN,
            strKprX500DNM_STREET,
            strKprX500DNM_BUSINESS_CATEGORY,
            strKprX500DNM_POSTAL_CODE,
            strKprX500DNM_DN_QUALIFIER,
            strKprX500DNM_PSEUDONYM,
            strKprX500DNM_DATE_OF_BIRTH,
            strKprX500DNM_PLACE_OF_BIRTH,
            strKprX500DNM_GENDER,
            strKprX500DNM_COUNTRY_OF_CITIZENSHIP,
            strKprX500DNM_COUNTRY_OF_RESIDENCE,
            strKprX500DNM_NAME_AT_BIRTH,
            strKprX500DNM_POSTAL_ADDRESS,

            strKprX520N_SURNAME,
            strKprX520N_GIVENNAME,
            strKprX520N_INITIALS,
            strKprX520N_GENERATION,
            strKprX520N_UNIQUE_IDENTIFIER,
            
            KTLAbs.f_s_strProviderKstBC // provider for keystore
            );
    }
    
    // ---------
    // PROTECTED
    
    protected boolean __doJob__(
        KeyStore kstOpen, 
            
        // below: about PKTC (Private Key & Trusted Certificate)
        String[] strsAliasPKTC, 
        Boolean[] boosIsTCEntryPKTC, 
        Boolean[] boosValidDatePKTC, 
        Boolean[] boosSelfSignedCertPKTC, 
        Boolean[] boosTrustedCertPKTC, 
        String[] strsSizeKeyPublPKTC,
        String[] strsTypeCertPKTC, 
        String[] strsAlgoSigCertPKTC, 
        Date[] dtesLastModifiedPKTC,
        // below: about SK (Secret Key)
        String[] strsAliasSK,
        Date[] dtesLastModifiedSK
        
        /*
        String[] strsAlias,
        Boolean[] boosEntryKpr,
        Boolean[] boosEntryTcr,
        Boolean[] boosSelfSignedCert,
        Boolean[] boosTrustedCert,
        String[] strsAlgoKeyPubl,
        String[] strsSizeKeyPubl,
        String[] strsTypeCert,
        String[] strsAlgoSigCert,
        Date[] dtesLastModified*/)
    {
        String strMethod = "__doJob__(...)";
        // ----
        
        // MEMO: overwriting alias not allowed
        // -----

        // ----
        // show dialog KeyPair new Rsa
        //  . get aliasKpr
       
        
        DTblsKstViewKeySavePKNoPass dlg = new DTblsKstViewKeySavePKNoPass(
            (Component) super._frmOwner_, 
           
            kstOpen,
            super._strPathAbsKst_,
            "Create RSA private key entry");
        
        if (! dlg.init())
            MySystem.s_printOutExit(this, strMethod, "failed");
        
        // 
        if (! dlg.load(
            
            // below: about PKTC (Private Key & Trusted Certificate)
            strsAliasPKTC, 
            boosIsTCEntryPKTC, 
            boosValidDatePKTC, 
            boosSelfSignedCertPKTC, 
            boosTrustedCertPKTC, 
            strsSizeKeyPublPKTC,
            strsTypeCertPKTC, 
            strsAlgoSigCertPKTC, 
            dtesLastModifiedPKTC,
            // below: about SK (Secret Key)
            strsAliasSK,
            dtesLastModifiedSK
                
            /*strsAlias, 
            boosEntryKpr, 
            boosEntryTcr,
            boosSelfSignedCert, 
            boosTrustedCert, 
            strsAlgoKeyPubl,
            strsSizeKeyPubl,
            strsTypeCert, strsAlgoSigCert, dtesLastModified*/))
        {
            MySystem.s_printOutExit(this, strMethod, "failed");
        }   
        
        dlg.setVisible(true);
        
        String strAliasKpr = dlg.getAlias();
        
        if (strAliasKpr == null)
        {
            MySystem.s_printOutTrace(this, strMethod, "nil strAliasKpr, aborted by user");
            return false;
        }
        
        // ----
        // create new KeyPair
        
        KeyPair kprNew = super._getKprNew_();
        
        if (kprNew == null)
        {
            MySystem.s_printOutError(this, strMethod, "nil kprNew");
            return false;
        }
        
        // ----
        // create new certificate of type X.509
        // memo: sig algo: MD5withRSA, MD2withRSA, SHA1withRSA, (RIPEMD160withRSA: IN COMMENTS, not supported by SunRsaSign)
        X509Certificate crtNew = super._getX509CertNew_(kprNew);
        
        if (crtNew == null)
        {
            MySystem.s_printOutError(this, strMethod, "nil crtNew");
            return false;
        }
        
        // ----
        // assign new entry to open keystore
        
        if (! this._assignNewEntry2OpenKeystore(kstOpen, kprNew, crtNew, strAliasKpr))
        {
            MySystem.s_printOutError(this, strMethod, "failed");
            return false;
        }
        
        return true;
    }
    
    
    /**
            ONLY FOR PKCS12
            
        MEMO: if alias already existing in keystore
        will be overwritten!!
        ... BUT already tested in calling code ==> never be the case
        
        if any code error, exiting
        else if any other error, show warning dialog, then return false;
        else return true
    **/
    private boolean _assignNewEntry2OpenKeystore(
        KeyStore kstOpen,
        KeyPair kprNew,
        X509Certificate crtX509New,
        String strAliasKpr
        )
    {
        String strMethod = "_assignNewEntry2OpenKeystore(...)";
        
        if (kstOpen==null || kprNew==null || crtX509New==null || strAliasKpr==null)
        {
            MySystem.s_printOutExit(this, strMethod, "nil arg");
        }
        
        
        boolean blnAlreadyListed = false;
        
        try
        {
            blnAlreadyListed = kstOpen.containsAlias(strAliasKpr);
        }
        
        catch(KeyStoreException excKeyStore)
        {
            excKeyStore.printStackTrace();
            MySystem.s_printOutExit(this, strMethod, "excKeyStore caught");
        }
        
        if (blnAlreadyListed) // should never  appear, as it has been checked in the calling code.
        {
            MySystem.s_printOutWarning(this, strMethod, "blnAlreadyListed, will be overwritten");
            
            
            /**String strBody = "keystore already contains an alias named:";
            strBody += "\n";
            strBody += "  ";
            strBody += "\"";
            strBody += strAliasKpr;
            strBody += "\"";
            
            strBody += "\n\n";
            strBody += "(Memo: aliases are case-insensitive)";
            
                
            OPAbstract.s_showDialogWarning(
                super._frmOwner_, strBody);
                
            return false;**/
        }
        
        // --
        
        PrivateKey pkyKeyPrivate = kprNew.getPrivate();
        
        if (pkyKeyPrivate == null)
        {
            MySystem.s_printOutExit(this, strMethod, "nil pkyKeyPrivate");
        }
        
        if (! UtilKstPkcs12.s_setKeyEntry(super._frmOwner_, 
            kstOpen, strAliasKpr, pkyKeyPrivate, new X509Certificate[]{ crtX509New }))
        {
            MySystem.s_printOutError(this, strMethod, "failed");
            return false;
        }
        
        return true;
    }
    
}