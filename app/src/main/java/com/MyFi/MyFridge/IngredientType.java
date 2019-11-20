package com.MyFi.MyFridge;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientType {
    List<String> meat =new ArrayList<String>(
            Arrays.asList("닭고기","닭고기(날개)","닭고기(봉)","돼지고기","쇠고기","쇠고기(차돌박이)","오리고기")
    );
    List<String> fish =new ArrayList<String>(
            Arrays.asList("갈치","고등어","꽁치","대하","미역","바지락","오징어","칵테일새우","다시마","새우")
    );
    List<String> vegetable =new ArrayList<String>(
            Arrays.asList("가지","감자","고구마","고사리","꽈리고추","느타리버섯","무","부추","상추","숙주","애호박","양송이버섯","양파","오이"
            ,"인삼","콩나물","토마토","통마늘","고추","깻잎","당근","대추","대파","마늘","미나리","버섯","브로콜리","생강","쪽파","통깨",
                    "파프리카","표고버섯","피망","황기","마늘종","양배추","새송이버섯")
    );
    List<String> milk =new ArrayList<String>(
            Arrays.asList("우유","치즈","생크림")
    );
    List<String> processed =new ArrayList<String>(
            Arrays.asList("가쓰오부시","당면","두부","만두","버터","베이컨","사골육수","소시지","순두부","스파게티면","스팸","어묵","참치캔",
                    "청국장","된장","카레","캔옥수수","토마토소스","고추장","새우젓","두반장","라면","쌈장")
    );
    List<String> other =new ArrayList<String>(
            Arrays.asList("계란","굴소스","김치","깍두기","떡","마요네즈","밀가루","부침가루","찹쌀","간장","고춧가루","꿀","다진마늘","김",
                    "마른멸치","맛술","머스타드소스","레몬즙","물엿","설탕","소금","올리고당","참기름","케첩","후추","튀김가루","돈까스소스"
            ,"꺠소금","매실청","건새우","식초","들기름")
    );

}
