package models.validators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import models.Report;

public class ReportValidator {
    public static List<String> validate(Report r) {
        //errorsが入るリスト
        List<String> errors = new ArrayList<String>();

        //validateTitle呼び出してtitle_errorに入れる
        String title_error = _validateTitle(r.getTitle());
        //title_errorが空でないならerrors Listにtitle_errorを入れる
        if(!title_error.equals("")) {
            errors.add(title_error);
        }

        String content_error = _validateContent(r.getContent());
        if(!content_error.equals("")) {
            errors.add(title_error);
        }

        String time_error = _validateTime(r.getSyukkin(),r.getTaikin());
        if(!time_error.equals("")){
            errors.add(time_error);
        }
        return errors;

    }


    private static String _validateTitle(String title) {
        if(title == null || title.equals("")) {
            return "タイトルを入力してください。";
            }

        return "";
    }

    private static String _validateContent(String content) {
        if(content == null || content.equals("")) {
            return "内容を入力してください。";
            }

        return "";
    }


    private static String _validateTime(String syukkin , String taikin ) {

        if (syukkin.equals(taikin)) {
            // 時間が一緒だったらエラー
            return "出勤時間と退勤時間は同じ時間は設定できません。";
        }

        //インスタンス作成
        Calendar syukkinCal = Calendar.getInstance();
        Calendar taikinCal = Calendar.getInstance();

        // 出勤時間を設定、List作ってsplitで分ける
        String[] syukkinList = syukkin.split(":");
        //時分だけ比較したいから年月日秒は固定でint型にして時分をリストに入れる
        syukkinCal.set(2000, 1, 1, Integer.parseInt(syukkinList[0]), Integer.parseInt(syukkinList[1]), 0);
        // 退勤時間を設定
        String[] taikinList = taikin.split(":");
        taikinCal.set(2000, 1, 1, Integer.parseInt(taikinList[0]), Integer.parseInt(taikinList[1]), 0);

        //.compareToは比較、左の値が大きいならば正
        int diff = syukkinCal.compareTo(taikinCal);

        //正なら出勤時間の方が大きい（遅い）
        if (diff > 0) {
            // エラー
            return "出勤時間は退勤時間より前の時間で入力してください。";
        }

        return "";
    }

}
