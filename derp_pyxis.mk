#
# Copyright (C) 2020 The UnDerp 2020
#
# SPDX-License-Identifier: Apache-2.0
#

# Inherit some common Derp stuff.
$(call inherit-product, vendor/aosip/config/common_full_phone.mk)

# Inherit from pyxis device.
$(call inherit-product, $(LOCAL_PATH)/device.mk)

# Boot animation FHD:
TARGET_BOOT_ANIMATION_RES := 1080

# Device identifier. This must come after all inclusions.
PRODUCT_DEVICE := pyxis
PRODUCT_NAME := derp_pyxis
PRODUCT_BRAND := Xiaomi
PRODUCT_MANUFACTURER := Xiaomi

PRODUCT_BUILD_PROP_OVERRIDES += \
    PRODUCT_NAME="pyxis" \
    TARGET_DEVICE="pyxis"

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi

# Miui Camera Mod AOSP Custom
$(call inherit-product, vendor/MiuiCamera/config.mk)
