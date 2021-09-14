package br.com.herco.todoappmvp.activities.home;

import br.com.herco.todoappmvp.models.UserModel;

public interface IHomeContract {
    interface IHomeView {
        void getCurrentUserSuccess(UserModel userModel);

        void getCurrentUserError(String message);
    }

    interface IHomePresenter {
        void getCurrentUser();
    }
}
