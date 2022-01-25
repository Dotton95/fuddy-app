package my.dotton.fuddy_app.Model

data class CovidResponse(
    val response: Response
)

data class Response(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode:String,
    val resultMsg:String
)
data class Body(
    val items:Items,
    val numOfRows:Int,
    val pageNo:Int,
    val totalCount:Int
)

data class Items(
    val item:List<Item>
)

data class Item(
    val createDt:String,
    val deathCnt:Int,
    val defCnt:Int,
    val gubun:String,
    val gubunCn:String,
    val gubunEn:String,
    val incDec:Int,
    val isolClearCnt:Int,
    val isolIngCnt:Int,
    val localOccCnt:Int,
    val overFlowCnt:Int,
    val qurRate:Any,
    val seq:Int,
    val stdDay:String,
    val updateDt:String
)