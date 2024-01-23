//
//  BookList.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import AsyncReactor
import shared

struct BookList: View {
    
    var bookList : [LocalBook]
    
    var body: some View {
        ForEach(bookList, id: \.bookId) { book in
            NavigationLink(destination: ReactorView(
                BookReactor()
            ) {
                BookView(bookId: book.bookId)
                
            }) {
                
                if let title = book.title, let image = book.imageLink, let authors = book.authors {
                    BookDataRow(
                        title: title,
                        image: image,
                        authors: authors.components(separatedBy: ";")
                    )
                }
            }
        }
    }
}
