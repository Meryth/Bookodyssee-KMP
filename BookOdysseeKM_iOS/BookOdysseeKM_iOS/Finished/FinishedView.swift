//
//  FinishedView.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 24.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import AsyncReactor

struct FinishedView: View {
    
    @EnvironmentObject
    private var reactor: FinishedReactor
    
    var body: some View {
        NavigationStack {
            VStack{
                List {
                    BookList(bookList: reactor.finishedList)
                }
            }.task {
                reactor.send(.loadBooks)
            }
            .navigationTitle("Finished")
        }
    }
}

#Preview {
    FinishedView()
}
