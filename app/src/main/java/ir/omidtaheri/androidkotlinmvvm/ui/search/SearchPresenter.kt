/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package ir.omidtaheri.androidkotlinmvvm.ui.search

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import ir.omidtaheri.androidkotlinmvvm.data.DataManager
import ir.omidtaheri.androidkotlinmvvm.data.network.model.SearchNameResponse
import ir.omidtaheri.androidkotlinmvvm.ui.base.BasePresenter
import ir.omidtaheri.androidkotlinmvvm.utils.rx.SchedulerProvider
import javax.inject.Inject


class SearchPresenter<V : SearchMvpView> @Inject constructor(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable
) : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SearchMvpPresenter<V> {
    override fun searchFirstPage(query: String) {
        mvpView?.visibility_progressBar(true)
        mCompositeDisposable.add(
            mDataManager.SearchName(query, 1)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(Consumer<SearchNameResponse> { response ->
                    if (!isViewAttached) {
                        return@Consumer
                    }
                    mvpView?.SetTotalPage(response.metadata?.pageCount!!)
                    mvpView?.setupListofSearch(response.data, query)
                }, Consumer<Throwable> { throwable ->
                    if (!isViewAttached) {
                        return@Consumer
                    }
                    mvpView?.error_load_first_page(handleApiError(throwable), query)
                })
        )
    }

    override fun searchNextPage(page: Int, query: String) {
        mCompositeDisposable.add(
            mDataManager.SearchName(query, page)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(Consumer<SearchNameResponse> { response ->
                    if (!isViewAttached) {
                        return@Consumer
                    }
                    mvpView?.sucssed_load_next_page(response.data)
                }, Consumer<Throwable?> { throwable ->
                    if (!isViewAttached) {
                        return@Consumer
                    }
                    mvpView?.error_load_next_page(query, page, handleApiError(throwable!!))
                })
        )
    }
}