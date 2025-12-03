"use client";

import { auth, googleProvider } from "@/lib/firebase";
import { User, createUserWithEmailAndPassword, onAuthStateChanged, signInWithEmailAndPassword, signInWithPopup, signOut, updateProfile } from "firebase/auth";
import React, { Children, ReactNode, createContext, useContext, useEffect } from "react";

type AppUser = {
    uid: string;
    email: string | null;
    displayName: string | null;
}

type AuthContextType = {
    user: AppUser | null;
    loading: boolean;
    token: string | null;
    login: (email: string, password: string) => Promise<any>;
    signup: (email: string, password: string, name: string) => Promise<any>;
    loginWithGoogle: () => Promise<any>;
    logout: () => Promise<void>;
  };

  const AuthContext = createContext<AuthContextType>({
    user: null,
    loading: true,
    token: null,
    login: async () => {},
    signup: async () => {},
    loginWithGoogle: async () => {},
    logout: async () => {},
  });

export function AuthProvider({ children }: {children: ReactNode}) {

    const [user, setUser] = React.useState<AppUser | null>(null);
    const [loading, setLoading] = React.useState(true);
    const [token, setToken] = React.useState<string | null>(null);

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, async (firebaseUser: User | null) => {
            if (firebaseUser) {

                const token = await firebaseUser.getIdToken();

                setUser({
                    uid: firebaseUser.uid,
                    email: firebaseUser.email,
                    displayName: firebaseUser.displayName || null
                });

                setToken(token);
            } else {
                setUser(null);
                setToken(null);
            }
            setLoading(false);
        });
        return () => unsubscribe();
    }, []);

    const login = async (email: string, password: string) => {
        return signInWithEmailAndPassword(auth, email, password);
    }

    const signup = async (email: string, password: string, name: string) => {
        const userCredentials = await createUserWithEmailAndPassword(auth, email, password);
        const user = userCredentials.user;

        // Updating the display name.
        await updateProfile(user, {
            displayName: name,
        });

        return user;
    }

    const loginWithGoogle = async () => {
        return signInWithPopup(auth, googleProvider);
    }

    const logout = async () => {
        
        return signOut(auth);
    }

    return (
        <AuthContext.Provider value={{
            user,
            loading,
            token,
            login,
            signup,
            loginWithGoogle,
            logout
        }}>
         {children}
         </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);