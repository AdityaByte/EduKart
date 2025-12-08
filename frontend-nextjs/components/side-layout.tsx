"use client";

import {
  Filter,
  Check
} from "lucide-react";
import { Separator } from "./ui/separator";
import { ScrollArea, ScrollBar } from "./ui/scroll-area";

export default function SideLayout() {
  return (
    <div className="p-4 space-y-6">
      <div className="space-y-2">
        <h3 className="font-semibold text-lg flex items-center gap-2">
          <Filter className="h-4 w-4" />
          Categories
        </h3>
        <Separator />
      </div>

      <ScrollArea className="w-full pr-2">
        <div className="space-y-3">
          {[
            "Programming",
            "Notes",
            "Projects",
            "Mathematics",
            "Design",
            "Competitive Programming"
          ].map((category) => (
            <div key={category} className="flex items-center justify-between">
              <span className="text-sm font-medium hover:text-primary cursor-pointer">
                {category}
              </span>
              <Check className="h-4 w-4 text-primary opacity-0 group-hover:opacity-100" />
            </div>
          ))}
        </div>
        <ScrollBar />
      </ScrollArea>

      <Separator />

      <div className="space-y-3">
        <h4 className="font-medium text-sm">Price Range</h4>
        <div className="space-y-2 text-xs">
          <label className="flex items-center gap-2">
            <input type="radio" name="price" className="w-4 h-4" />
            <span>₹0 - ₹100</span>
          </label>
          <label className="flex items-center gap-2">
            <input type="radio" name="price" className="w-4 h-4" />
            <span>₹100 - ₹500</span>
          </label>
          <label className="flex items-center gap-2">
            <input type="radio" name="price" className="w-4 h-4" />
            <span>₹500+</span>
          </label>
        </div>
      </div>

      <button className="w-full text-xs text-muted-foreground border border-border rounded-md py-2 hover:bg-accent">
        Clear Filters
      </button>
    </div>
  );
}
