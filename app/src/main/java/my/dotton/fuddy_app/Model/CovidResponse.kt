package my.dotton.fuddy_app.Model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml


@Xml(name="response")
data class CovidResponse(
    @Element
    val header: Header,
    @Element
    val body: Body
)

@Xml(name="header")
data class Header(
    @PropertyElement
    val resultCode:String,
    @PropertyElement
    val resultMsg:String
)
@Xml(name="body")
data class Body(
    @Element
    val items:Items,
    @PropertyElement
    val numOfRows:Int,
    @PropertyElement
    val pageNo:Int,
    @PropertyElement
    val totalCount:Int
)

@Xml(name="items")
data class Items(
    @Element
    val item:List<Item>
)

@Xml(name="item")
data class Item(
    //게시글번호
    @PropertyElement(name = "seq")
    val seq: String,
    //기준일
    @PropertyElement(name = "stateDt")
    val stateDt: String,
    //기준시간
    @PropertyElement(name = "stateTime")
    val stateTime: String,
    //확진자 수
    @PropertyElement(name = "decideCnt")
    val decideCnt: String,
    //사망자 수
    @PropertyElement(name = "deathCnt")
    val deathCnt: String,
    //누적 의심 신고 검사자
    @PropertyElement(name = "accExamCnt")
    val accExamCnt: String?=null,
    //누적 확진률
    @PropertyElement(name = "accDefRate")
    val accDefRate: String?=null,
    //등록일시
    @PropertyElement(name = "createDt")
    val createDt: String,
    //수정일시
    @PropertyElement(name = "updateDt")
    val updateDt: String
)