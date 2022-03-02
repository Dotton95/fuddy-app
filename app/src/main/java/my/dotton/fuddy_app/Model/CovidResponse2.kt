package my.dotton.fuddy_app.Model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name="response")
data class CovidResponse2(
    @Element
    val header: Header2,
    @Element
    val body: Body2
)

@Xml(name="header")
data class Header2(
    @PropertyElement
    val resultCode:String,
    @PropertyElement
    val resultMsg:String
)
@Xml(name="body")
data class Body2(
    @Element
    val items:Items2,
    @PropertyElement
    val numOfRows:Int,
    @PropertyElement
    val pageNo:Int,
    @PropertyElement
    val totalCount:Int
)

@Xml(name="items")
data class Items2(
    @Element
    val item:List<Item_covid2>
)

@Xml(name="item")
data class Item_covid2(
    //확진자 수
    @PropertyElement(name = "defCnt")
    val defCnt:String,
    //시도명
    @PropertyElement(name = "gubun")
    val gubun:String,
    //전일대비 증감수
    @PropertyElement(name = "incDec")
    val incDec:String,
    //해외유입 수
    @PropertyElement(name = "overFlowCnt")
    val overFlowCnt:String,
    //지역발생 수
    @PropertyElement(name = "localOccCnt")
    val localOccCnt:String,
    //사망자 수
    @PropertyElement(name = "deathCnt")
    val deathCnt: String,
    //10만명당 발생률
    @PropertyElement(name = "qurRate")
    val qurRate: String,
    //기준시간
    @PropertyElement(name = "stdDay")
    val stdDay: String
)