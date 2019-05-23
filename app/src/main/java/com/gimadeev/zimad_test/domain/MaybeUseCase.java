package com.gimadeev.zimad_test.domain;

import com.gimadeev.zimad_test.domain.executor.PostExecutionThread;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class MaybeUseCase<T, P> {

    private PostExecutionThread postExecutionThread;
    private CompositeDisposable disposables;

    public MaybeUseCase(PostExecutionThread postExecutionThread) {
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    abstract protected Maybe<T> buildMaybe(P param);

    public void execute(P param, Consumer<T> onNext, Consumer<Throwable> onError) {
        Maybe<T> maybe = this.buildMaybe(param)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.getScheduler());

        addDisposable(maybe.subscribe(onNext, onError));
    }

    public void dispose() {
        disposables.clear();
    }

    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
