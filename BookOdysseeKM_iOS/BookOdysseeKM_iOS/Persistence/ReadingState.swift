//
//  ReadingState.swift
//  BookOdysseeKM_iOS
//
//  Created by Elna on 23.01.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum ReadingState : String {
    case notAdded
    case toRead
    case reading
    case finished
    
    var description : String {
        switch self {
        case .notAdded: return "NOT_ADDED"
        case .toRead: return "TO_READ"
        case .reading: return "READING"
        case .finished: return "FINISHED"
        }
    }
}
