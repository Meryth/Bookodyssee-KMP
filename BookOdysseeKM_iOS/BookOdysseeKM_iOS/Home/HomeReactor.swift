//
//  HomeReactor.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import AsyncReactor
import shared

class HomeReactor: AsyncReactor {
    
    enum Action {
        case loadBooks
    }
    
    struct State {
        var toReadList: [LocalBook] = []
        var currentlyReadingList: [LocalBook] = []
    }
    
    @Published
    private(set) var state: State
    
    private let bookOdysseeSDK: BookOdysseeSDK
    private let settings: Multiplatform_settingsSettings
    
    @MainActor
    init(
        state: State = State(),
        bookOdysseeSDK: BookOdysseeSDK = Config.bookOdysseeSDK,
        settings: Multiplatform_settingsSettings = Config.settings
    ) {
        self.state = state
        self.bookOdysseeSDK = bookOdysseeSDK
        self.settings = settings
    }
    
    func action(_ action: Action) async {
        switch action {
        case .loadBooks:
            do {
                let savedBooks = try bookOdysseeSDK.getAllBooksByUser(userId: settings.getLong(key: "userId", defaultValue: 0))
                
                print("settings")
                print(settings.getLongOrNull(key: "userId"))
                print(savedBooks)
                
                state.currentlyReadingList = savedBooks.filter { book in
                    return book.readingState == ReadingState.reading.description
                }
                
                state.toReadList = savedBooks.filter { book in
                    return book.readingState == ReadingState.toRead.description
                }
            } catch {
                print("Error when fetching books!")
                print(error)
            }
        }
    }
    
}
