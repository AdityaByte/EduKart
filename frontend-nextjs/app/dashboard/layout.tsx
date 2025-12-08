import SideLayout from "@/components/side-layout";

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
    return (
      <div className="min-h-screen flex">
        <aside className="w-80 px-4 bg-card/30 backdrop-blur ">
          <SideLayout />
        </aside>
        <main className="w-full p-10">
          {children}
        </main>

      </div>
    );
  }
