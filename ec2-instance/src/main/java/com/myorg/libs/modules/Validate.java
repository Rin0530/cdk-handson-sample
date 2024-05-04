package com.myorg.libs.modules;

import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class Validate {
    /**
     * cdk.jsonのcontextを検証する
     * 不正な項目があれば警告を出し終了する
     * @param context
     */
    static public void validate(Map<String,String> context) {
        if(!validateCidr(context.get("cidr"))) {
            System.err.println("cidrが不正です\n0~255の整数を入力してください");
            System.exit(1);
        }
        if(!validateCreator(context.get("creator"))) {
            System.err.println("creatorが不正です\n1文字以上の半角英数で入力してください");
            System.exit(1);
        }
        if(!validateCpuType(context.get("cpuType"))) {
            System.err.println("cpuTypeが不正です\nx86_64かarm64のどちらかを入力してください");
            System.exit(1);
        }
        if(!validateInstanceSize(context.get("instanceSize"))) {
            System.err.println("instanceSizeが不正です\nmediumかlargeのどちらかを入力してください");
            System.exit(1);
        }
        if(!validateCount(Integer.parseInt(context.get("count")))) {
            System.out.println("countが不正です\\n1~5の整数を入力してください");
            System.exit(1);
        }
    }

    /**
     * cidrの検証
     * 0~255以外ならfalseを返す
     * @param cidr
     * @return boolean
     */
    static private boolean validateCidr(String cidr) {
        try {
            int intCidr = Integer.parseInt(cidr);
            return intCidr >= 0 && intCidr <= 255;
        } catch (Exception e) {
            return false;
        }
        
    }

    /**
     * creatorの検証
     * 英数字1文字以上以外ならfalseを返す
     * @param creator
     * @return boolean
     */
    static private boolean validateCreator(String creator) {
        return creator.matches("^[A-Za-z0-9-]+$");
    }

    /**
     * cpuTypeの検証
     * x86_64とarm64のどちらか以外(大文字小文字は問わない)であればfalseを返す
     * @param cpuType
     * @return boolean
     */
    static private boolean validateCpuType(String cpuType) {
        List<String> cpuTypeList = Arrays.asList("x86_64", "arm64");
        return cpuTypeList.contains(cpuType.toLowerCase());
    }

    /**
     * instanceSizeの検証
     * mediumとlargeのどちらか以外(大文字小文字は問わない)であればfalseを返す
     * @param instanceSize
     * @return boolean
     */
    static private boolean validateInstanceSize(String instanceSize) {
        List<String> instanceSizeList = Arrays.asList("medium", "large");
        return instanceSizeList.contains(instanceSize.toLowerCase());
    }
    
    /**
     * instance台数の検証
     * あまり大量に作られても困るのでMAX5台に制限
     * @param count
     * @return
     */
    static private boolean validateCount(int count) {
        return count > 0 && count <= 10;
    }
}
