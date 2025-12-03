"use client";

import { auth, googleProvider } from "@/lib/firebase";
import { User, createUserWithEmailAndPassword, onAuthStateChanged, signInWithEmailAndPassword, signInWithPopup, signOut } from "firebase/auth";
import React, { Children, ReactNode, createContext, useContext, useEffect } from "react";

type AppUser = {
    uid: string;
    email: string | null;
    displayName: string | null;
}

type AuthContextType = {
    user: AppUser | null;
    loading: boolean;
    login: (email: string, password: string) => Promise<any>;
    signup: (email: string, password: string) => Promise<any>;
    loginWithGoogle: () => Promise<any>;
    logout: () => Promise<void>;
  };

  const AuthContext = createContext<AuthContextType>({
    user: null,
    loading: true,
    login: async () => {},
    signup: async () => {},
    loginWithGoogle: async () => {},
    logout: async () => {},
  });

export function AuthProvider({ children }: {children: ReactNode}) {

    const [user, setUser] = React.useState<AppUser | null>(null);
    const [loading, setLoading] = React.useState(true);

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, (firebaseUser: User | null) => {
            if (firebaseUser) {
                setUser({
                    uid: firebaseUser.uid,
                    email: firebaseUser.email,
                    displayName: firebaseUser.displayName || firebaseUser.email?.split("@")[0] || null
                });
            } else {
                setUser(null);
            }
            setLoading(false);
        });
        return () => unsubscribe();
    }, []);

    const login = async (email: string, password: string) => {
        return signInWithEmailAndPassword(auth, email, password);
    }

    const signup = async (email: string, password: string) => {
        return createUserWithEmailAndPassword(auth, email, password);
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