package com.michael.afrivac.Util;

import android.os.AsyncTask;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// backend api
// https://currencylayer.com/documentation

// Converts from USD to any of the supported currencies below

// Supported Currencies

//        AED
//        United Arab Emirates Dirham

//        AFN
//        Afghan Afghani

//        ALL
//        Albanian Lek

//        AMD
//        Armenian Dram

//        ANG
//        Netherlands Antillean Guilder
//
//
//        AOA
//        Angolan Kwanza
//
//
//        ARS
//        Argentine Peso
//
//
//        AUD
//        Australian Dollar
//
//
//        AWG
//        Aruban Florin
//
//
//        AZN
//        Azerbaijani Manat
//
//
//        BAM
//        Bosnia-Herzegovina Convertible Mark
//
//
//        BBD
//        Barbadian Dollar
//
//
//        BDT
//        Bangladeshi Taka
//
//
//        BGN
//        Bulgarian Lev
//
//
//        BHD
//        Bahraini Dinar
//
//
//        BIF
//        Burundian Franc
//
//
//        BMD
//        Bermudan Dollar
//
//
//        BND
//        Brunei Dollar
//
//
//        BOB
//        Bolivian Boliviano
//
//
//        BRL
//        Brazilian Real
//
//
//        BSD
//        Bahamian Dollar
//
//
//        BTC
//        Bitcoin
//
//
//        BTN
//        Bhutanese Ngultrum
//
//
//        BWP
//        Botswanan Pula
//
//
//        BYN
//        Belarusian Ruble
//
//
//        BYR
//        Belarusian Ruble
//
//
//        BZD
//        Belize Dollar
//
//
//        CAD
//        Canadian Dollar
//
//
//        CDF
//        Congolese Franc
//
//
//        CHF
//        Swiss Franc
//
//
//        CLF
//        Chilean Unit of Account (UF)
//
//
//        CLP
//        Chilean Peso
//
//
//        CNY
//        Chinese Yuan
//
//
//        COP
//        Colombian Peso
//
//
//        CRC
//        Costa Rican Colón
//
//
//        CUC
//        Cuban Convertible Peso
//
//
//        CUP
//        Cuban Peso
//
//
//        CVE
//        Cape Verdean Escudo
//
//
//        CZK
//        Czech Republic Koruna
//
//
//        DJF
//        Djiboutian Franc
//
//
//        DKK
//        Danish Krone
//
//
//        DOP
//        Dominican Peso
//
//
//        DZD
//        Algerian Dinar
//
//
//        EEK
//        Estonian Kroon
//
//
//        EGP
//        Egyptian Pound
//
//
//        ERN
//        Eritrean Nakfa
//
//
//        ETB
//        Ethiopian Birr
//
//
//        EUR
//        Euro
//
//
//        FJD
//        Fijian Dollar
//
//
//        FKP
//        Falkland Islands Pound
//
//
//        GBP
//        British Pound Sterling
//
//
//        GEL
//        Georgian Lari
//
//
//        GGP
//        Guernsey Pound
//
//
//        GHS
//        Ghanaian Cedi
//
//
//        GIP
//        Gibraltar Pound
//
//
//        GMD
//        Gambian Dalasi
//
//
//        GNF
//        Guinean Franc
//
//
//        GTQ
//        Guatemalan Quetzal
//
//
//        GYD
//        Guyanaese Dollar
//
//
//        HKD
//        Hong Kong Dollar
//
//
//        HNL
//        Honduran Lempira
//
//
//        HRK
//        Croatian Kuna
//
//
//        HTG
//        Haitian Gourde
//
//
//        HUF
//        Hungarian Forint
//
//
//        IDR
//        Indonesian Rupiah
//
//
//        ILS
//        Israeli New Sheqel
//
//
//        IMP
//        Manx pound
//
//
//        INR
//        Indian Rupee
//
//
//        IQD
//        Iraqi Dinar
//
//
//        IRR
//        Iranian Rial
//
//
//        ISK
//        Icelandic Króna
//
//
//        JEP
//        Jersey Pound
//
//
//        JMD
//        Jamaican Dollar
//
//
//        JOD
//        Jordanian Dinar
//
//
//        JPY
//        Japanese Yen
//
//
//        KES
//        Kenyan Shilling
//
//
//        KGS
//        Kyrgystani Som
//
//
//        KHR
//        Cambodian Riel
//
//
//        KMF
//        Comorian Franc
//
//
//        KPW
//        North Korean Won
//
//
//        KRW
//        South Korean Won
//
//
//        KWD
//        Kuwaiti Dinar
//
//
//        KYD
//        Cayman Islands Dollar
//
//
//        KZT
//        Kazakhstani Tenge
//
//
//        LAK
//        Laotian Kip
//
//
//        LBP
//        Lebanese Pound
//
//
//        LKR
//        Sri Lankan Rupee
//
//
//        LRD
//        Liberian Dollar
//
//
//        LSL
//        Lesotho Loti
//
//
//        LTL
//        Lithuanian Litas
//
//
//        LVL
//        Latvian Lats
//
//
//        LYD
//        Libyan Dinar
//
//
//        MAD
//        Moroccan Dirham
//
//
//        MDL
//        Moldovan Leu
//
//
//        MGA
//        Malagasy Ariary
//
//
//        MKD
//        Macedonian Denar
//
//
//        MMK
//        Myanma Kyat
//
//
//        MNT
//        Mongolian Tugrik
//
//
//        MOP
//        Macanese Pataca
//
//
//        MRO
//        Mauritanian Ouguiya
//
//
//        MUR
//        Mauritian Rupee
//
//
//        MVR
//        Maldivian Rufiyaa
//
//
//        MWK
//        Malawian Kwacha
//
//
//        MXN
//        Mexican Peso
//
//
//        MYR
//        Malaysian Ringgit
//
//
//        MZN
//        Mozambican Metical
//
//
//        NAD
//        Namibian Dollar
//
//
//        NGN
//        Nigerian Naira
//
//
//        NIO
//        Nicaraguan Córdoba
//
//
//        NOK
//        Norwegian Krone
//
//
//        NPR
//        Nepalese Rupee
//
//
//        NZD
//        New Zealand Dollar
//
//
//        OMR
//        Omani Rial
//
//
//        PAB
//        Panamanian Balboa
//
//
//        PEN
//        Peruvian Nuevo Sol
//
//
//        PGK
//        Papua New Guinean Kina
//
//
//        PHP
//        Philippine Peso
//
//
//        PKR
//        Pakistani Rupee
//
//
//        PLN
//        Polish Zloty
//
//
//        PYG
//        Paraguayan Guarani
//
//
//        QAR
//        Qatari Rial
//
//
//        RON
//        Romanian Leu
//
//
//        RSD
//        Serbian Dinar
//
//
//        RUB
//        Russian Ruble
//
//
//        RWF
//        Rwandan Franc
//
//
//        SAR
//        Saudi Riyal
//
//
//        SBD
//        Solomon Islands Dollar
//
//
//        SCR
//        Seychellois Rupee
//
//
//        SDG
//        Sudanese Pound
//
//
//        SEK
//        Swedish Krona
//
//
//        SGD
//        Singapore Dollar
//
//
//        SHP
//        Saint Helena Pound
//
//
//        SLL
//        Sierra Leonean Leone
//
//
//        SOS
//        Somali Shilling
//
//
//        SRD
//        Surinamese Dollar
//
//
//        STD
//        São Tomé and Príncipe Dobra
//
//
//        SVC
//        Salvadoran Colón
//
//
//        SYP
//        Syrian Pound
//
//
//        SZL
//        Swazi Lilangeni
//
//
//        THB
//        Thai Baht
//
//
//        TJS
//        Tajikistani Somoni
//
//
//        TMT
//        Turkmenistani Manat
//
//
//        TND
//        Tunisian Dinar
//
//
//        TOP
//        Tongan Paʻanga
//
//
//        TRY
//        Turkish Lira
//
//
//        TTD
//        Trinidad and Tobago Dollar
//
//
//        TWD
//        New Taiwan Dollar
//
//
//        TZS
//        Tanzanian Shilling
//
//
//        UAH
//        Ukrainian Hryvnia
//
//
//        UGX
//        Ugandan Shilling
//
//
//        USD
//        United States Dollar
//
//
//        UYU
//        Uruguayan Peso
//
//
//        UZS
//        Uzbekistan Som
//
//
//        VEF
//        Venezuelan Bolívar Fuerte
//
//
//        VND
//        Vietnamese Dong
//
//
//        VUV
//        Vanuatu Vatu
//
//
//        WST
//        Samoan Tala
//
//
//        XAF
//        CFA Franc BEAC
//
//
//        XAG
//        Silver (troy ounce)
//
//
//        XAU
//        Gold (troy ounce)
//
//
//        XCD
//        East Caribbean Dollar
//
//
//        XDR
//        Special Drawing Rights
//
//
//        XOF
//        CFA Franc BCEAO
//
//
//        XPF
//        CFP Franc
//
//
//        YER
//        Yemeni Rial
//
//
//        ZAR
//        South African Rand
//
//
//        ZMK
//        Zambian Kwacha (pre-2013)
//
//
//        ZMW
//        Zambian Kwacha
//
//
//        ZWL
//        Zimbabwean Dollar

public class CurrencyConverter {

    public static String TAG = "CurrencyConverter";

    // Base currency must always be USD
    public static final String BASE_CURRENCY = "USD";

    public static double convertedCurrency;

    public static double conversionValue;

    public static double convert(final String baseCurrency, final String convertedToCurrency, final double value) {

        // convertedToCurrency and BASE_CURRENCY cannot be the same
        if (convertedToCurrency.equals(BASE_CURRENCY)) {

            return -1;

        }


        class CurrencyTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                StringBuilder result = new StringBuilder();

                try {

                    URL url = new URL("http://apilayer.net/api/live?access_key=e9cee497fe1e131c4cdc0b96a71b1548&currencies=" + convertedToCurrency + "&source=" + BASE_CURRENCY + "&format=1");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;

                    while ((line = reader.readLine()) != null) {

                        result.append(line + "\n");

                    }

                    // json format
                    //http://apilayer.net/api/live?access_key=e9cee497fe1e131c4cdc0b96a71b1548&currencies=NGN&source=USD&format=1
                    // {"success":true,"terms":"https:\/\/currencylayer.com\/terms","privacy":
                    // "https:\/\/currencylayer.com\/privacy","timestamp":1592843826,
                    // "source":"USD","quotes":{"USDNGN":387.504014}}

                    // extracting json from result string
                    JSONObject jsonObject = new JSONObject(result.toString());
                    conversionValue = Double.parseDouble(jsonObject.getJSONObject("quotes").getString("USD" + convertedToCurrency));


                    convertedCurrency = value * conversionValue;


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;

            }

        }

        new CurrencyTask().execute();


        return convertedCurrency;

    }


}
