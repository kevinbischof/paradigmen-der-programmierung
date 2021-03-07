module Composition where

import Prelude

import Effect (Effect)
import Effect.Console as Console

incr :: Int -> Int
incr x = x + 1

quad :: Int -> Int
quad x = x * x

main :: Effect Unit
main = (incr >>> quad >>> Console.logShow) 1