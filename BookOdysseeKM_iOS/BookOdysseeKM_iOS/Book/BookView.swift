//
//  BookView.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import AsyncReactor

struct BookView: View {
    
    @EnvironmentObject
    private var reactor : BookReactor
    
    var bookId : String
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                
                if let book = reactor.book {
                    
                    Group {
                        Text(book.volumeInfo.title)
                            .font(.largeTitle)
                            .frame(alignment: .top)
                            .padding(.bottom)
                        
                        if let image = book.volumeInfo.imageLinks?.thumbnail {
                            AsyncImage(url: URL(string: image))
                                .frame(width: /*@START_MENU_TOKEN@*/100/*@END_MENU_TOKEN@*/, height: 140)
                                .scaledToFit()
                        } else {
                            Image("NoImagePlaceholder")
                                .resizable()
                                .frame(width: /*@START_MENU_TOKEN@*/100/*@END_MENU_TOKEN@*/, height: 140)
                                .scaledToFit()
                        }
                    }.frame(
                        maxWidth: .infinity,
                        alignment: Alignment.center
                    ).padding(.bottom)
                    
                    
                    Divider()
                        .padding()
                    
                    if let authors = book.volumeInfo.authors {
                        DataRow(
                            label: "Author",
                            value: getAuthorString(authorList: authors)
                        )
                    }
                    
                    if let publisher = book.volumeInfo.publisher {
                        DataRow(label: "Publisher", value: publisher)
                    }
                    
                    DataRow(label: "Published", value: book.volumeInfo.publishedDate)
                    
                    
                    if let pageCount = book.volumeInfo.pageCount {
                        let nsPageCount = pageCount as NSNumber
                        DataRow(label: "Page count:", value: String(nsPageCount.intValue))
                    }
                    
                    Divider()
                    
                    if let description = book.volumeInfo.description_ {
                        Text(description)
                            .fixedSize(horizontal: false, vertical: true)
                            .padding()
                    }
                    
                    
                    Spacer()
                    
                    VStack() {
                        if(reactor.readingState == ReadingState.notAdded.description) {
                            Button(action: {
                                reactor.send(.addBookToReadingList)
                            }) {
                                Text("Add to reading list")
                                    .padding(15)
                                    .frame(maxWidth: .infinity)
                                    .background(Color("Primary"))
                                    .foregroundColor(.white)
                            }
                        } else {
                            if(reactor.readingState == ReadingState.toRead.description) {
                                Button(action: {
                                    reactor.send(.startReadingBook)
                                }) {
                                    Text("Start reading")
                                        .padding(15)
                                        .frame(maxWidth: .infinity)
                                        .background(Color("Primary"))
                                        .foregroundColor(.white)
                                }
                            }
                            
                            if(reactor.readingState == ReadingState.toRead.description || reactor.readingState == ReadingState.reading.description) {
                                Button(action: {
                                    reactor.send(.finishBook)
                                }) {
                                    Text("Finish")
                                        .padding(15)
                                        .frame(maxWidth: .infinity)
                                        .background(Color("Primary"))
                                        .foregroundColor(.white)
                                }
                            }
                            
                            Button(action: {
                                reactor.send(.removeBookFromReadingList)
                            }) {
                                Text("Remove from reading list")
                                    .padding(15)
                                    .frame(maxWidth: .infinity)
                                    .background(Color("Primary"))
                                    .foregroundColor(.white)
                            }
                        }
                    }.padding(.horizontal)
                    
                    Spacer()
                }
            }.task {
                reactor.send(.loadBookData(bookId))
            }
        }
    }
}

func getAuthorString(authorList: [String]) -> String {
    var fullAuthorList = ""
    
    for author in authorList {
        if fullAuthorList.isEmpty {
            fullAuthorList.append(author)
        } else {
            fullAuthorList.append(", \(author)")
        }
    }
    
    return fullAuthorList
}
