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
    
    var body: some View {
        
        NavigationStack {
            ZStack {
                if reactor.searchResult.isEmpty {
                    Text("No result!")
                } else {
                    List(reactor.searchResult, id: \.id) { book in                        
                        NavigationLink(destination: ReactorView(
                            BookReactor()
                        ) {
                            BookView(bookId: book.id)
                        }) {
                            BookDataRow(
                                title: book.volumeInfo.title,
                                image: book.volumeInfo.imageLinks?.thumbnail,
                                authors:  book.volumeInfo.authors
                            )
                        }
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
