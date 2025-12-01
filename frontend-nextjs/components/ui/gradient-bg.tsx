"use client";

export function SoftGradientBackground() {
  return (
    <div className="absolute inset-0 -z-10 pointer-events-none">
      <div className="absolute inset-0 bg-[#f5f5f7]" />  {/* base background */}

      <div className="absolute left-0 top-0 w-[600px] h-[900px] -rotate-45 -translate-y-40 rounded-full
      bg-[radial-gradient(70%_70%_at_50%_40%,rgba(200,200,200,0.45)_0,rgba(160,160,160,0.15)_60%,transparent_100%)]" />

      <div className="absolute left-20 top-0 w-[400px] h-[700px] -rotate-45 translate-y-20 rounded-full
      bg-[radial-gradient(60%_60%_at_50%_50%,rgba(220,220,220,0.35)_0,rgba(150,150,150,0.1)_70%,transparent_100%)]" />

      <div className="absolute left-40 top-0 w-[450px] h-[700px] -rotate-45 -translate-y-20 rounded-full
      bg-[radial-gradient(50%_50%_at_50%_50%,rgba(180,180,180,0.3)_0,rgba(120,120,120,0.08)_80%,transparent_100%)]" />
    </div>
  );
}
