package com.yemensoft.mynewgriddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.print.PdfPrint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yemensoft.htmlreportslibrary.Activities.HtmlReportViewer;
import com.yemensoft.htmlreportslibrary.UI.MyTableView;
import com.yemensoft.htmlreportslibrary.Utilities.ReportModel;
import com.yemensoft.htmlreportslibrary.Utilities.TableLister;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitl;
    private MyTableView WebTable;
    private Button btnAdd,btn_showReport,btn_Share;

    ArrayList<ItmModel> mItmModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        AddItmToGrid();
    }


    public void initViews() {
        tvTitl = (TextView) findViewById(R.id.tv_titl);
        WebTable = (MyTableView) findViewById(R.id.WebTable);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_Share.setVisibility(View.GONE);
                AddItmToGrid();
            }
        });

        btn_showReport=(Button) findViewById(R.id.btn_showReport);

        btn_showReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddItmToGrid();
                AddItmToGrid();
                btn_Share.setVisibility(View.VISIBLE);
                ShowReport(1,mItmModelsList);
            }
        });

        btn_Share =(Button) findViewById(R.id.btn_Share);
        btn_Share.setVisibility(View.GONE);
        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               shareReport();
            }
        });
    }


    private void AddItmToGrid() {
        if(mItmModelsList==null)
        {
            mItmModelsList=new ArrayList<>();
        }

        ItmModel itmModel=new ItmModel();

        itmModel.itm_no=String.valueOf(mItmModelsList.size()+1);
        itmModel.itm_name=" صنف رقم " +itmModel.itm_no;
        itmModel.itm_uint="قطعة" ;
        itmModel.itm_info="صنف تجريبي " ;


        mItmModelsList.add(itmModel);
        ShowDocDtlList(1,mItmModelsList);

    }


    private void ShowDocDtlList(int lng, ArrayList<ItmModel> itmModelArrayList) {



        ReportModel reportModel = new ReportModel();


        reportModel.langId=lng;

        reportModel.ReportHtmlFileName="Grid.html";
        reportModel.largColIndex=1;
        reportModel.PaperSize=80;


        JSONArray jsonArray = new JSONArray();



        jsonArray.put("رقم الصنف");
        jsonArray.put("اسم الصنف");
        jsonArray.put("الوحدة");
        jsonArray.put("الوصف");




        reportModel.DTLTableHeaderJsonArrayDataSnglRow=jsonArray.toString();



        JSONArray jsonout = new JSONArray();

        if(itmModelArrayList!=null) {
            for (ItmModel dbitm : itmModelArrayList) {


                jsonArray = new JSONArray();



                jsonArray.put(""+dbitm.itm_no);
                jsonArray.put(""+dbitm.itm_name);
                jsonArray.put(dbitm.itm_uint);
                jsonArray.put(""+dbitm.itm_info);


                jsonout.put(jsonArray);

            }

            reportModel.DTLJsonArrayDataMultiRow = jsonout.toString();

            jsonArray = new JSONArray();

            jsonArray.put("اجمالي الاصناف ");
            jsonArray.put(""+ mItmModelsList.size());
            reportModel.FooterJsonArrayDataSnglRow=jsonArray.toString();

        }




        WebTable.ShowTable(reportModel, new TableLister() {
            @Override
            public void printTable(String imagbase64FromHtml) {

            }

            @Override
            public void OnTableRowClicked(String TableRowAsJsonArray) {
                String data[]=TableRowAsJsonArray.replace("[","").replace("]","").replace("\"","").split(",");

            }
        });


    }

    private void ShowReport(int lng, ArrayList<ItmModel> itmModelArrayList) {



        ReportModel reportModel = new ReportModel();


        reportModel.langId=lng;
        reportModel.ReportName="كشف حساب ";
        reportModel.ReportHtmlFileName="GenReportA4.html";
        reportModel.largColIndex=1;
        reportModel.PaperSize=80;




        JSONArray jsonArray = new JSONArray();



        jsonArray.put("الرصيد الافتتاحي");
        jsonArray.put("5000 ريال");
        jsonArray.put("الرصيد الختامي ");
        jsonArray.put("3000 ريال");

        reportModel.MSTJsonArrayDataSnglRow=jsonArray.toString();

         jsonArray = new JSONArray();
        jsonArray.put("رقم الصنف");
        jsonArray.put("اسم الصنف");
        jsonArray.put("الوحدة");
        jsonArray.put("الوصف");




        reportModel.DTLTableHeaderJsonArrayDataSnglRow=jsonArray.toString();



        JSONArray jsonout = new JSONArray();

        if(itmModelArrayList!=null) {
            for (ItmModel dbitm : itmModelArrayList) {


                jsonArray = new JSONArray();



                jsonArray.put(""+dbitm.itm_no);
                jsonArray.put(""+dbitm.itm_name);
                jsonArray.put(dbitm.itm_uint);
                jsonArray.put(""+dbitm.itm_info);


                jsonout.put(jsonArray);

            }

            reportModel.DTLJsonArrayDataMultiRow = jsonout.toString();

            jsonArray = new JSONArray();

            jsonArray.put("اجمالي الاصناف ");
            jsonArray.put(""+ mItmModelsList.size());
            reportModel.FooterJsonArrayDataSnglRow=jsonArray.toString();

        }


     //   HtmlReportViewer htmlReportViewer=new HtmlReportViewer(this);
       // htmlReportViewer.Show(reportModel);

        WebTable.ShowReport(reportModel,null);


    }


    private void shareReport() {
        WebTable.Share();
    }
}
