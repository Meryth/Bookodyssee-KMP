//
//  BookReactor.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import AsyncReactor
import shared
import KMPNativeCoroutinesAsync
import KMPNativeCoroutinesCore

class BookReactor: AsyncReactor {
    
    enum Action {
        case loadBookData(String)
        case addBookToReadingList
        case removeBookFromReadingList
        case startReadingBook
        case finishBook
    }
    
    struct State {
        var book: BookItem? = nil
        var readingState: String = ReadingState.notAdded.description
        var localBook: LocalBook? = nil
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
        case .loadBookData(let bookId):
            do {
                let sequence = asyncSequence(for: DataRepo().getBookById(id: bookId))
                for try await result in sequence {
                    state.book = result
                }
                
                let savedBooks = try bookOdysseeSDK.getBook(userId: settings.getLong(key: "userId", defaultValue: 0), bookId: bookId)
                
                if savedBooks == nil {
                    state.readingState = ReadingState.notAdded.description
                } else {
                    guard let readingState = savedBooks?.readingState else {
                        print("ReadingState must not be nil!")
                        return
                    }
                    state.readingState = readingState
                }
            } catch {
                print("Error when fetching book data!")
                print(error)
            }
        case .addBookToReadingList:
            do {
                guard let bookItem = state.book else {
                    print("Book cannot be nil!")
                    return
                }
                
                try bookOdysseeSDK.addBookToReadingList(
                    book: LocalBook(
                        userId: settings.getLong(key: "userId", defaultValue: 0),
                        bookId: bookItem.id,
                        title: bookItem.volumeInfo.title,
                        authors: bookItem.volumeInfo.authors?.joined(separator: ";"),
                        publisher: bookItem.volumeInfo.publisher,
                        publishedDate: bookItem.volumeInfo.publishedDate,
                        pageCount: 0,
                        imageLink: bookItem.volumeInfo.imageLinks?.thumbnail,
                        readingState: ReadingState.toRead.description
                    )
                )
                state.readingState = ReadingState.toRead.description
            } catch {
                print("Error when adding book!")
                print(error)
            }
        case .removeBookFromReadingList:
            do {
                guard let bookItem = state.book else {
                    print("Book cannot be nil!")
                    return
                }
                
                try bookOdysseeSDK.removeBookFromReadingList(userId: settings.getLong(key: "userId", defaultValue: 0), bookId: bookItem.id)
                state.readingState = ReadingState.notAdded.description
            } catch {
                print("Error when removing book!")
                print(error)
            }
        case .startReadingBook:
            do {
                guard let bookItem = state.book else {
                    print("Book cannot be nil!")
                    return
                }
                
                try bookOdysseeSDK.updateReadingState(readingState: ReadingState.reading.description, userId: settings.getLong(key: "userId", defaultValue: 0), bookId: bookItem.id)
                state.readingState = ReadingState.reading.description
            } catch {
                print("Error when starting book!")
                print(error)
            }
        case .finishBook:
            do {
                guard let bookItem = state.book else {
                    print("Book cannot be nil!")
                    return
                }
                
                try bookOdysseeSDK.updateReadingState(readingState: ReadingState.finished.description, userId: settings.getLong(key: "userId", defaultValue: 0), bookId: bookItem.id)
                state.readingState = ReadingState.finished.description
            } catch {
                print("Error when finishing book!")
                print(error)
            }
        }
    }
}
