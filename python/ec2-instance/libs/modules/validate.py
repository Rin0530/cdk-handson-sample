import re
import sys

def validateContext(context:dict):
    """
    cdk.jsonのcontextを検証する
    不正な項目があれば警告を出し終了する
    """
    if not _validateCidr(context.get("cidr")):
        print("cidrが不正です\n0~255の整数を入力してください",file=sys.stderr)
        exit(1)
    if not _validateCreator(context.get("creator")):
        print("creatorが不正です\n1文字以上の半角英数で入力してください",file=sys.stderr)
        exit(1)
    if not _validateCpuType(context.get("cpuType")):
        print("cpuTypeが不正です\nx86_64かarm64のどちらかを入力してください",file=sys.stderr)
        exit(1)
    if not _validateInstanceSize(context.get("instanceSize")):
        print("instanceSizeが不正です\nmediumかlargeのどちらかを入力してください",file=sys.stderr)
        exit(1)
    if not _validateCount(context.get("count")):
        print("countが不正です\n1~5の整数を入力してください",file=sys.stderr)
        exit(1)

def _validateCidr(cidr:str) -> bool:
    """
    cidrの検証
    0~255以外ならfalseを返す
    """
    try:
        numberCidr = int(cidr)
        return numberCidr >= 0 and numberCidr <= 255
    except:
        return False
    
def _validateCreator(creator:str) -> bool:
    """
    creatorの検証
    英数字1文字以上以外ならfalseを返す
    """
    return re.match(r'^[a-zA-Z0-9-]+$', creator)
    
def _validateCpuType(cpuType:str) -> bool:
    """
    cpuTypeの検証
    x86_64とarm64のどちらか以外(大文字小文字は問わない)であればfalseを返す
    """
    return cpuType.lower() in ["x86_64", "arm64"]

def _validateInstanceSize(instanceSize:str) -> bool:
    """
    instanceSizeの検証
    mediumとlargeのどちらか以外(大文字小文字は問わない)であればfalseを返す
    """
    return instanceSize.lower() in ["medium", "large"]

def _validateCount(count:str) -> bool:
    """
    instance台数の検証
    あまり大量に作られても困るのでMAX5台に制限
    """
    numberCount = int(count)
    return numberCount >= 1 and numberCount <= 5