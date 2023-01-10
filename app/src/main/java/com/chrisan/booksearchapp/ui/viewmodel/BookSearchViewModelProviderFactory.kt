package com.chrisan.booksearchapp.ui.viewmodel

//class BookSearchViewModelProviderFactory(
//    private val bookSearchRepository: BookSearchRepository,
//    private val workManager: WorkManager,
//    owner: SavedStateRegistryOwner,
//    defaultArgs: Bundle? = null,
//) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//    override fun <T : ViewModel> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T {
//        if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
//            return BookSearchViewModel(bookSearchRepository, workManager, handle) as T
//        }
//        throw java.lang.IllegalArgumentException("ViewModel class not found")
//    }
//}