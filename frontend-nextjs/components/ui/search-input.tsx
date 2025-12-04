"use client";

import React, { useState, useRef, useEffect } from "react";
import { Search } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

export default function SearchInput({ searchOpen, setSearchOpen }: { searchOpen: boolean; setSearchOpen: React.Dispatch<React.SetStateAction<boolean>> }) {
    const wrapperRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const handler = (e: MouseEvent) => {
            if (wrapperRef.current && !wrapperRef.current.contains(e.target as Node)) {
                setSearchOpen(false);
            }
        };
        document.addEventListener("mousedown", handler);
        return () => document.removeEventListener("mousedown", handler);
    }, []);

    return (
        <div
            ref={wrapperRef}
            className="relative flex items-center"
        >
            {!searchOpen && (
                <Button
                    variant="ghost"
                    size="icon"
                    onClick={() => setSearchOpen(true)}
                    className="rounded-full"
                >
                    <Search className="h-4 w-4" />
                </Button>
            )}

            <div
                className={`
          absolute right-0 top-1/2 -translate-y-1/2
          transition-all duration-300 ease-in-out
          ${searchOpen ? "w-64 opacity-100" : "w-0 opacity-0"}
          overflow-hidden
        `}
            >
                <Input
                    autoFocus
                    type="text"
                    placeholder="Search…"
                    className="h-9 pr-8 pl-3"
                />

                <Search className="absolute right-2 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
            </div>
        </div>
    );
}
