package top.wzmyyj.zymk.model.net;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import top.wzmyyj.zymk.model.box.HomeBox;

/**
 * Created by yyj on 2018/07/09. email: 2209011667@qq.com
 */

public class HomeModel {

    public static Document mDocument;

    public void getData(Observer<HomeBox> observer) {
        Observable.create(new ObservableOnSubscribe<HomeBox>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<HomeBox> observableEmitter) throws Exception {
                try {
                    if (mDocument == null) {
                        mDocument = Jsoup.connect(Urls.ZYMK_HomePage).get();
                    }
                    Document doc = mDocument;
                    HomeBox data = DocUtil.transToHome(doc);
                    observableEmitter.onNext(data);
                } catch (Exception e) {
                    observableEmitter.onError(e);
                    e.printStackTrace();
                } finally {
                    observableEmitter.onComplete();
                }
            }

        })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }


}
