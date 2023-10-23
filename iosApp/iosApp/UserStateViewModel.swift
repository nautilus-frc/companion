//
//  UserStateViewModel.swift
//  iosApp
//
//  Created by Jason Ballinger on 7/17/23.
//  Copyright © 2023 team2658. All rights reserved.
//

import Foundation
import shared

enum UserStateError: Error {
    case signInError, signOutError, createAccountError
}

@MainActor
class UserStateViewModel: ObservableObject {
    
    @Published var isLoggedIn = false
    @Published var isBusy = false
    @Published var userOut: shared.User?
    let defaults = UserDefaults.standard
    
    func signIn(username: String, password: String) async -> Result<Bool, UserStateError> {
        isBusy = true
        do {
            let client = EmotionClient()
            let response = try await client.login(username: username, password: password)
            
            if let response {
                print(response.firstName)
                userOut = response
//                defaults.set(response, forKey: "User")
//                let meow = defaults.object(forKey: "User")
//                print(meow)
                isLoggedIn = true
                isBusy = false
                return .success(true)
            } else {
                // TODO: Provide error message to user via dialog or screen
                print("fail")
                isBusy = false
                return .failure(.signInError)
            }
        } catch {
            isBusy = false
            return .failure(.signInError)
        }
    }
    
    func createAccount(firstname: String, lastname: String, username: String, email: String, password: String, subteam: shared.Subteam, phone: String, grade: Int32) async -> Result<Bool, UserStateError> {
        isBusy = true
        do {
            let client = EmotionClient()
            let response = try await client.register(username: username, password: password, email: email, firstName: firstname, lastName: lastname, subteam: subteam, phone: phone, grade: grade)
            
            // DONT CLEAR THE WARNING IT BREAKS THE CODE
            if let response {
                userOut = response
                isLoggedIn = true
                isBusy = false
                return .success(true)
            } else {
                isBusy = false
                return .failure(.createAccountError)
            }
        } catch {
            isBusy = false
            return .failure(.createAccountError)
        }
    }
    
    func signOut() async -> Result<Bool, UserStateError> {
        isBusy = true
        do {
            userOut = nil
            isLoggedIn = false
            isBusy = false
            return .success(true)
        } // catch {
//            isBusy = false
//            return .failure(.signOutError)
//        }
    }
    
    func setLoggedOut() {
        isLoggedIn = false
    }
}
