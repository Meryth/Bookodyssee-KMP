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
    
//    let defaults = UserDefaults.standard
    
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
    
    @MainActor
    init(state: State = State()) {
        self.state = state
    }
    
    func action(_ action: Action) async {
        switch action {
        case .loadBookData(let bookId):
            do {
                let sequence = asyncSequence(for: DataRepo().getBookById(id: bookId))
                for try await result in sequence {
                    state.book = result
                    print(result)
                }
                
//                state.book = try await apiClient.getBookById(endpoint: .getBookById(id: bookId))
//                
//                let savedBooks : NSFetchRequest<LocalBook> = LocalBook.fetchRequest()
//                if let bookId = state.book?.id {
//                    guard let userId = defaults.string(forKey: "UserId") else {
//                        throw CoreException.NilPointerError
//                    }
//                    savedBooks.predicate = NSPredicate(format: "bookId == %@ AND userId == %@", bookId, userId)
//                    let numberOfBooks = try moc.count(for: savedBooks)
//                    
//                    if numberOfBooks <= 0 {
//                        state.readingState = ReadingState.notAdded.description
//                    } else {
//                        let books = try moc.fetch(savedBooks).first
//                        guard let savedReading = books?.readingState else {
//                            throw CoreException.NilPointerError
//                        }
//                        state.readingState = savedReading
//                    }
//                }
            } catch {
                print("Error when fetching book data!")
                print(error)
            }
        case .addBookToReadingList:
            do {
//                guard let bookItem = state.book else {
//                    print("Book cannot be nil!")
//                    throw CoreException.NilPointerError
//                }
//                
//                convertBookItemToLocalBook(bookItem: bookItem)
//                
//                try moc.save()
//                state.readingState = ReadingState.toRead.description
            } catch {
                print("Error while saving book to DB!")
                print(error)
            }
        case .removeBookFromReadingList:
            do {
//                let savedBooks : NSFetchRequest<LocalBook> = LocalBook.fetchRequest()
//                
//                if let bookId = state.book?.id {
//                    guard let userId = defaults.string(forKey: "UserId") else {
//                        print("UserId is nil!")
//                        return
//                    }
//                    savedBooks.predicate = NSPredicate(format: "bookId == %@ AND userId == %@", bookId, userId)
//                    
//                    do {
//                        let objects = try moc.fetch(savedBooks).first
//                        
//                        if let localBook = objects {
//                            moc.delete(localBook)
//                            
//                            do {
//                                try moc.save()
//                                state.readingState = ReadingState.notAdded.description
//                            } catch {
//                                print("Error while deleting book from DB!")
//                                print(error)
//                                
//                            }
//                        }
//                    } catch {
//                        print("Unable to fetch saved books from DB!")
//                        print(error)
//                    }
//                }
            }
        case .startReadingBook:
            do {
//                let savedBooks : NSFetchRequest<LocalBook> = LocalBook.fetchRequest()
//                
//                guard let userId = defaults.string(forKey: "UserId") else {
//                    print("UserId is nil!")
//                    return
//                }
//                
//                if let bookId = state.book?.id {
//                    savedBooks.predicate = NSPredicate(format: "bookId == %@ AND userId", bookId, userId)
//                    
//                    do {
//                        let objects = try moc.fetch(savedBooks).first
//                        
//                        if let localBook = objects {
//                            localBook.setValue(ReadingState.reading.description, forKey: "readingState")
//                            
//                            do {
//                                try moc.save()
//                                state.readingState = ReadingState.reading.description
//                            } catch {
//                                print("Error while updating reading state!")
//                                print(error)
//                            }
//                        }
//                    } catch {
//                        print("Unable to fetch saved books from DB!")
//                        print(error)
//                    }
                }
        case .finishBook:
            do {
//                let savedBooks : NSFetchRequest<LocalBook> = LocalBook.fetchRequest()
//
//                guard let userId = defaults.string(forKey: "UserId") else {
//                    print("UserId is nil!")
//                    return
//                }
//
//                if let bookId = state.book?.id {
//                    savedBooks.predicate = NSPredicate(format: "bookId == %@ AND userId == %@", bookId, userId)
//
//                    do {
//                        let objects = try moc.fetch(savedBooks).first
//
//                        if let localBook = objects {
//                            localBook.setValue(ReadingState.finished.description, forKey: "readingState")
//
//                            do {
//                                try moc.save()
//                                state.readingState = ReadingState.finished.description
//                            } catch {
//                                print("Error while updating reading state!")
//                                print(error)
//                            }
//                        }
//                    } catch {
//                        print("Unable to fetch saved books from DB!")
//                        print(error)
//                    }
//                }
            }
        }
    }
}
