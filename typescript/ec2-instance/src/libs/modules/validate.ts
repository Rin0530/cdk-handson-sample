import { Context } from "./utils";

/**
 * cdk.jsonのcontextを検証する
 * 不正な項目があれば警告を出し終了する
 * @param context
 */
export function validateContext(context:Context) {
    if(!validateCidr(context.cidr)) {
        console.error("cidrが不正です\n0~255の整数を入力してください");
        process.exit(1)
    } 
    if(!validateCreator(context.creator)) {
        console.error("creatorが不正です\n1文字以上の半角英数で入力してください");
        process.exit(1)
    }
    if(!validateCpuType(context.cpuType)) {
        console.error("cpuTypeが不正です\nx86_64かarm64のどちらかを入力してください");
        process.exit(1)
    }
    if(!validateInstatanceSize(context.instanceSize)) {
        console.error("instanceSizeが不正です\nmediumかlargeのどちらかを入力してください");
        process.exit(1)
    }
    if(!validateCount(context.count)) {
        console.error("countが不正です\n1~5の整数を入力してください");
        process.exit(1)
    }
}

/**
 * cidrの検証
 * 0~255以外ならfalseを返す
 * @param cidr 
 * @returns boolean
 */
function validateCidr(cidr: string): boolean {
    const numberCidr = Number(cidr)
    return Number.isInteger(numberCidr) && numberCidr >= 0 && numberCidr <= 255;
}

/**
 * creatorの検証
 * 英数字1文字以上以外ならfalseを返す
 * @param creator 
 * @returns boolean
 */
function validateCreator(creator: string): boolean {
    return Boolean(creator.match(/^[A-Za-z0-9-]+$/));
}

/**
 * cpuTypeの検証
 * x86_64とarm64のどちらか以外(大文字小文字は問わない)であればfalseを返す
 * @param cpuType 
 * @returns boolean
 */
function validateCpuType(cpuType: string): boolean {
    const cpuTypeList = ["x86_64", "arm64"]
    return Boolean(cpuTypeList.includes(cpuType.toLowerCase()));
}

/**
 * instanceSizeの検証
 * mediumとlargeのどちらか以外(大文字小文字は問わない)であればfalseを返す
 * @param instanceSize 
 * @returns boolean
 */
function validateInstatanceSize(instanceSize: string): boolean {
    const instanceSizeList = ["medium", "large"]
    return Boolean(instanceSizeList.includes(instanceSize.toLowerCase()));
}

/**
 * instance台数の検証
 * あまり大量に作られても困るのでMAX5台に制限
 * @param count 
 * @returns 
 */
function validateCount(count: string): boolean {
    const numberCount = Number(count)
    return Number.isInteger(numberCount) && numberCount >= 1 && numberCount <= 5;
}