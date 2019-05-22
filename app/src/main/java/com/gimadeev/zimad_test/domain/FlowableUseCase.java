package com.gimadeev.zimad_test.domain;

import com.gimadeev.zimad_test.domain.executor.PostExecutionThread;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class FlowableUseCase<T, P> {

    private PostExecutionThread postExecutionThread;
    private CompositeDisposable disposables;

    public FlowableUseCase(PostExecutionThread postExecutionThread) {
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    abstract protected Flowable<T> buildFlowable(P param);

    public void execute(P param, Consumer<T> onNext, Consumer<Throwable> onError, Action onComplete) {
        Flowable<T> flowable = this.buildFlowable(param)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler());

        addDisposable(flowable.subscribe(onNext, onError, onComplete));
    }

    public void dispose() {
        disposables.clear();
    }

    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
