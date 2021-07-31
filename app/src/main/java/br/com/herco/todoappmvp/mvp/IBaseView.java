package br.com.herco.todoappmvp.mvp;

public interface IBaseView<T> {
    T loadPresenter();

    void onViewReady();
}