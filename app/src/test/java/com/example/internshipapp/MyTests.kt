package com.example.internshipapp

import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.usecases.AuthUseCase
import com.example.internshipapp.presentation.feature1.LoginViewModel
import com.example.internshipapp.presentation.feature2.PostsViewModel
import com.example.internshipapp.presentation.feature6.PaginationViewModel
import kotlinx.coroutines.delay
import org.junit.Test
import org.koin.core.component.get
import kotlin.test.assertEquals


class MyTests : BaseTestClass() {


    @Test
    fun testPostsFromNetwork() {
        test {
            val vm = get<PostsViewModel>()
            delay(100)
            assertEquals(vm.state.value.loadedPostsListFromNetwork.size, 3)

            vm.sendIntent(PostsViewModel.Intent.OnPostFilterTextChanged("2"))
            assertEquals(vm.state.value.filteredListOfPostEntities.size, 1)

        }
    }


    @Test
    fun testGetPostsFromDb() {
        test {
            val vm = get<PostsViewModel>()
            delay(100)
            assertEquals(vm.state.value.favouritePosts.size, 4)


        }
    }

    @Test
    fun addPostsToDb() {
        test {
            val vm = get<PostsViewModel>()
            delay(100)
            vm.sendIntent(
                PostsViewModel.Intent.FavouriteButtonClicked(
                    PostEntity(
                        id = 345,
                        title = "Title $1",
                        body = "Body $1",
                        userId = 1,
                        isFavourite = false
                    )
                )
            )
            delay(2000)
            assertEquals(vm.state.value.favouritePosts.size, 5)

        }

    }

    @Test
    fun removePostsFromDb() {
        test {
            val vm = get<PostsViewModel>()
            delay(3000)
            vm.sendIntent(
                PostsViewModel.Intent.FavouriteButtonClicked(
                    PostEntity(
                        id = 1,
                        title = "Title $1",
                        body = "Body $1",
                        userId = 1,
                        isFavourite = true
                    )
                )
            )
            delay(2000)
            assertEquals(vm.state.value.favouritePosts.size, 3)

        }

    }

    @Test
    fun testAuthorization() {
        test {
            val vm = get<LoginViewModel>()
            vm.sendIntent(LoginViewModel.Intent.OnLoginTextChange("User1"))
            vm.sendIntent(LoginViewModel.Intent.OnPasswordTextChange("12345678"))
            assertEquals(vm.state.value.userEntity.login, "User1")
            assertEquals(vm.state.value.userEntity.password, "12345678")

            val authUseCase = get<AuthUseCase>()
            assertEquals(authUseCase(vm.state.value.userEntity), true)
        }
    }

    @Test
    fun testPagination() {
        test {
            val vm = get<PaginationViewModel>()
            delay(10000)
            assertEquals(vm.state.value.dataList.size, 11)
            repeat(5){
                vm.sendIntent(PaginationViewModel.Intent.OnLoadNextPage)
            }
            delay(10000)
            assertEquals(vm.state.value.dataList.size, 22)
        }

    }

}
