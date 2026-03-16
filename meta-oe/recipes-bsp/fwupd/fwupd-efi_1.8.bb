SUMMARY = "EFI executable for fwupd"
LICENSE = "LGPL-2.1-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRC_URI = "git://github.com/fwupd/fwupd-efi;protocol=https;branch=main"
SRCREV = "8572a93e81e7110b88445d1907fdd73982557347"

DEPENDS = "gnu-efi python3-pefile-native"

COMPATIBLE_HOST = "(x86_64.*|i.86.*|aarch64.*|arm.*)-linux"

inherit meson pkgconfig python3native

# These should be configured as needed
SBAT_DISTRO_ID ?= "${DISTRO}"
SBAT_DISTRO_SUMMARY ?= "${DISTRO_NAME}"
SBAT_DISTRO_URL ?= ""

export CC_LD = "${HOST_PREFIX}ld"

EXTRA_OEMESON += "\
                  -Defi-includedir=${STAGING_INCDIR}/efi \
                  -Defi-libdir=${STAGING_LIBDIR} \
                  -Defi_sbat_distro_id='${SBAT_DISTRO_ID}' \
                  -Defi_sbat_distro_summary='${SBAT_DISTRO_SUMMARY}' \
                  -Defi_sbat_distro_url='${SBAT_DISTRO_URL}' \
                  -Defi_sbat_distro_pkgname='${PN}' \
                  -Defi_sbat_distro_version='${PV}'\
                  "

# The compile assumes GCC at present
TOOLCHAIN = "gcc"

# Multiple errors like below with gcc14
#| ../git/efi/fwupdate.c:611:20: error: passing argument 5 of 'fwup_log' from incompatible pointer type [-Wincompatible-pointer-types]
#|   611 |         fwup_debug(L"n_updates: %d", n_updates);
#|       |                    ^~~~~~~~~~~~~~~~
#TOOLCHAIN_OPTIONS += "-Wno-error=incompatible-pointer-types"
