"use client";

import Link from "next/link";
import { UserRound } from "lucide-react";
import { useSyncExternalStore } from "react";

type UserProfile = {
  name: string;
  email: string;
  phone?: string;
};

const readStoredUser = (): UserProfile | null => {
  if (typeof window === "undefined") {
    return null;
  }

  const saved = localStorage.getItem("qfome-user");
  if (!saved) {
    return null;
  }

  try {
    const parsed = JSON.parse(saved) as UserProfile;
    if (!parsed?.name || !parsed?.email) {
      return null;
    }
    return parsed;
  } catch {
    return null;
  }
};

export function HeaderAccountAction() {
  const user = useSyncExternalStore(
    (onStoreChange) => {
      if (typeof window === "undefined") {
        return () => {};
      }

      const handler = () => onStoreChange();
      window.addEventListener("storage", handler);
      window.addEventListener("qfome-user-changed", handler);
      return () => {
        window.removeEventListener("storage", handler);
        window.removeEventListener("qfome-user-changed", handler);
      };
    },
    () => readStoredUser(),
    () => null,
  );

  if (user) {
    const firstName = user.name.split(" ")[0] ?? "Cliente";
    return (
      <Link
        href="/cliente"
        className="hidden items-center gap-2 rounded-2xl border border-[#f0cabd] bg-white px-4 py-2 text-sm font-bold text-[#6a3b32] transition hover:bg-[#fff0e8] sm:inline-flex"
      >
        <UserRound size={16} />
        Ola, {firstName}
      </Link>
    );
  }

  return (
    <Link
      href="/entrar"
      className="hidden rounded-2xl border border-[#f0cabd] bg-white px-4 py-2 text-sm font-bold text-[#6a3b32] transition hover:bg-[#fff0e8] sm:inline-flex"
    >
      Entrar
    </Link>
  );
}
