package my.dotton.fuddy_app.Model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name="response")
data class AreaResponse(
    @Element
    val header: AreaHeader,
    @Element
    val body: AreaBody
)

@Xml(name="header")
data class AreaHeader(
    @PropertyElement
    val resultCode:String,
    @PropertyElement
    val resultMsg:String
)
@Xml(name="body")
data class AreaBody(
    @Element
    val items:AreaItems,
    @PropertyElement
    val numOfRows:Int,
    @PropertyElement
    val pageNo:Int,
    @PropertyElement
    val totalCount:Int
)

@Xml(name="items")
data class AreaItems(
    @Element
    val item:List<Item_Area>
)

@Xml(name="item")
data class Item_Area(
    //허가구역명
    @PropertyElement(name = "prmisnZoneNm")
    val prmisnZoneNm : String?=null,
    //시도명
    @PropertyElement(name = "ctprvnNm")
    val ctprvnNm: String?=null,
    //시군구명
    @PropertyElement(name = "signguNm")
    val signguNm: String?=null,
    //소재지지번주소
    @PropertyElement(name = "lnmadr")
    val lnmadr: String?=null,
    //위도
    @PropertyElement(name = "latitude")
    val latitude: String?=null,
    //경도
    @PropertyElement(name = "longitude")
    val longitude: String?=null,
    //푸드트럭운영대수
    @PropertyElement(name = "vhcleCo")
    val vhcleCo : String?=null,
    //허가구역 사용료
    @PropertyElement(name = "primsnZoneRntfee")
    val prmisnZoneRntfee: String?=null,
    //허가구역운영시작일자
    @PropertyElement(name = "beginDate")
    val beginDate: String?=null,
    //허가구역운영종료일자
    @PropertyElement(name = "endDate")
    val endDate: String?=null,
    //허가구역휴무일
    @PropertyElement(name = "rstde")
    val rstde: String?=null,
    //허가구역평일운영시작시각
    @PropertyElement(name = "weekdayOperOpenHhmm")
    val weekdayOperOpenHhmm : String?=null,
    //허가구역평일운영종료시각
    @PropertyElement(name = "weekdayOperColseHhmm")
    val weekdayOperColseHhmm : String?=null,
    //허가구역주말운영시작시각
    @PropertyElement(name = "wkendOperOpenHhmm")
    val wkendOperOpenHhmm : String?=null,
    //허가구역주말운영종료시각
    @PropertyElement(name = "wkendOperColseHhmm")
    val wkendOperColseHhmm : String?=null,
    //판매제한품목
    @PropertyElement(name = "lmttPrdlst")
    val lmttPrdlst : String?=null,
    //관리기관명
    @PropertyElement(name = "institutionNm")
    val institutionNm : String?=null,
    //관리기관 전화번호
    @PropertyElement(name = "phoneNumber")
    val phoneNumber: String?=null,
    //데이터 기준일자
    @PropertyElement(name = "referenceDate")
    val referenceDate : String?=null

)