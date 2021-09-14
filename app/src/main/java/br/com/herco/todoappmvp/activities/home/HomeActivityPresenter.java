package br.com.herco.todoappmvp.activities.home;

import br.com.herco.todoappmvp.repositories.user.IUserRepository;

public class HomeActivityPresenter implements IHomeContract.IHomePresenter {

    private final IHomeContract.IHomeView homeView;
    private final IUserRepository userRepository;

    public HomeActivityPresenter(IHomeContract.IHomeView homeView, IUserRepository userRepository) {
        this.homeView = homeView;
        this.userRepository = userRepository;
    }


    // TODO: to Remove. Tem que estar na splash, por exemplo
    @Override
    public void getCurrentUser() {
        try {
            //UserModel userModel = userRepository.getCurrentUser("any");
            //homeView.getCurrentUserSuccess(userModel);
        } catch (Exception ex) {
            homeView.getCurrentUserError("Error" + ex.getMessage());
        }
    }
}
