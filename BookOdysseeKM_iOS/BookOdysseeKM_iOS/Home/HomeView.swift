//
//  HomeView.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import AsyncReactor

struct HomeView: View {
    
    @EnvironmentObject
    private var reactor : HomeReactor
    
    var body: some View {
        NavigationStack {
            VStack {
                List {
                    Section(
                        header: Text("Currently reading")
                            .foregroundStyle(Color("Primary"))
                    ) {
                        BookList(bookList: reactor.currentlyReadingList)
                    }
                    
                    Section(
                        header: Text("Want to read")
                            .foregroundStyle(Color("Primary"))
                    ) {
                        BookList(bookList: reactor.toReadList)
                    }
                    
                }
                
            } .task {
                reactor.send(.loadBooks)
            }
            .navigationTitle("To Read")
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    NavigationLink(
                        destination: ReactorView(SearchReactor()) {
                            SearchView()
                        }
                    ) {
                        Image(systemName: "plus")
                            .renderingMode(.template)
                            .foregroundColor(Color("Primary"))
                    }
                }
            }
        }
    }
}

#Preview {
    HomeView()
}
