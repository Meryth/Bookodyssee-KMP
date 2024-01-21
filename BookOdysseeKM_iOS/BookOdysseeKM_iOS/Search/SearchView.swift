//
//  SearchView.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 21.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import AsyncReactor

struct SearchView: View {
    
    @EnvironmentObject
    private var reactor: SearchReactor
    
    //    @Environment(\.managedObjectContext) private var viewContext
    
    var body: some View {
        
        NavigationStack {
            ZStack {
                if reactor.searchResult.isEmpty {
                    Text("No result!")
                } else {
                    List(reactor.searchResult, id: \.id) { book in
                        
                        BookDataRow(
                            title: book.volumeInfo.title,
                            image: book.volumeInfo.imageLinks?.thumbnail,
                            authors:  book.volumeInfo.authors
                        )
                        
//                                                NavigationLink(destination: ReactorView(
//                                                    BookReactor(dbContext: viewContext)
//                                                ) {
//                                                    BookView(bookId: book.id)
//                                                }) {
//                                                    BookDataRow(
//                                                        title: book.volumeInfo.title,
//                                                        image: book.volumeInfo.imageLinks?.thumbnail,
//                                                        authors:  book.volumeInfo.authors
//                                                    )
//                                                }
                    }
                }
            }
            .navigationTitle("Search")
            .searchable(
                text: Binding(
                    get: {reactor.query},
                    set: {reactor.action(.onQueryChange($0))}
                ),
                prompt: "Enter title, author...")
            .onSubmit(of: .search) {
                reactor.send(.onSearchClick)
            }
        }
    }
}

#Preview {
    ReactorView(SearchReactor()) {
        SearchView()
    }
}
